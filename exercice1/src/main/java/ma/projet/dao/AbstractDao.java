package ma.projet.dao;

import java.util.List;
import java.util.function.Consumer;

import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * IDao 的通用抽象实现：所有服务类继承它即可获得基本的增删改查功能，避免重复代码。
 *
 * @param <T> 实体类型
 */
public abstract class AbstractDao<T> implements IDao<T> {

    // 实体的 Class 对象，用于 findById 和 findAll
    private final Class<T> type;

    protected AbstractDao(Class<T> type) {
        this.type = type;
    }

    /**
     * 在一个事务中执行写操作：成功则提交，失败则回滚。
     * 注意：回滚必须在关闭会话之前执行，因此这里不使用 try-with-resources。
     */
    protected boolean executeInTransaction(Consumer<Session> action) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            action.accept(session);
            tx.commit();
            return true;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();   // 出错时回滚事务
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();   // 最后关闭会话
        }
    }

    @Override
    public boolean create(T o) {
        return executeInTransaction(session -> session.persist(o));   // 保存新对象
    }

    @Override
    public boolean delete(T o) {
        // 如果对象是游离状态，先合并再删除
        return executeInTransaction(session -> session.remove(session.contains(o) ? o : session.merge(o)));
    }

    @Override
    public boolean update(T o) {
        return executeInTransaction(session -> session.merge(o));   // 更新对象
    }

    @Override
    public T findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(type, id);   // 根据主键查询
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from " + type.getSimpleName(), type).getResultList();
        }
    }
}
