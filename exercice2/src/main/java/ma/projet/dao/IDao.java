package ma.projet.dao;

import java.util.List;

/**
 * 通用 DAO 接口：定义所有服务类都要实现的基本增删改查操作。
 *
 * @param <T> 实体类型
 */
public interface IDao<T> {

    /** 新增一个对象 */
    boolean create(T o);

    /** 删除一个对象 */
    boolean delete(T o);

    /** 修改一个对象 */
    boolean update(T o);

    /** 根据主键查找对象 */
    T findById(int id);

    /** 查询所有对象 */
    List<T> findAll();
}
