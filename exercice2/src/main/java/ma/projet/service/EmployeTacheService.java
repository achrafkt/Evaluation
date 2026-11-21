package ma.projet.service;

import java.util.List;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTachePK;
import ma.projet.dao.AbstractDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

/**
 * 员工-任务服务：提供关联类 EmployeTache 的增删改查。
 */
public class EmployeTacheService extends AbstractDao<EmployeTache> {

    public EmployeTacheService() {
        super(EmployeTache.class);
    }

    /**
     * 保存员工-任务记录：@MapsId 不接受游离（detached）的员工和任务，
     * 因此先在当前会话中通过 getReference 重新关联它们，再保存。
     */
    @Override
    public boolean create(EmployeTache et) {
        return executeInTransaction(session -> {
            et.setEmploye(session.getReference(Employe.class, et.getEmploye().getId()));
            et.setTache(session.getReference(Tache.class, et.getTache().getId()));
            session.persist(et);
        });
    }

    /**
     * EmployeTache 使用复合主键，单个 int 主键无法定位，这里返回 null。
     * 请使用 findById(employeId, tacheId)。
     */
    @Override
    public EmployeTache findById(int id) {
        return null;
    }

    /** 根据复合主键（员工编号 + 任务编号）查找 */
    public EmployeTache findById(int employeId, int tacheId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(EmployeTache.class, new EmployeTachePK(employeId, tacheId));
        }
    }

    /** 查询所有记录（同时加载员工和任务） */
    @Override
    public List<EmployeTache> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from EmployeTache et join fetch et.employe join fetch et.tache",
                    EmployeTache.class).getResultList();
        }
    }
}
