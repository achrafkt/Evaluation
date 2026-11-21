package ma.projet.service;

import java.util.Date;
import java.util.List;

import ma.projet.beans.Femme;
import ma.projet.dao.AbstractDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

/**
 * 女人服务：除基本增删改查外，还提供原生命名查询和命名查询。
 */
public class FemmeService extends AbstractDao<Femme> {

    public FemmeService() {
        super(Femme.class);
    }

    /** 执行原生命名查询 "Femme.nbrEnfantsEntreDates"：某个女人在两个日期之间的子女总数 */
    public int nbrEnfantsEntreDates(Femme femme, Date d1, Date d2) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Object result = session.createNamedQuery("Femme.nbrEnfantsEntreDates")
                    .setParameter("id", femme.getId())
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getSingleResult();
            // MySQL 的 SUM 返回 BigDecimal，这里统一转换为 int
            return ((Number) result).intValue();
        }
    }

    /** 执行命名查询 "Femme.marieesAuMoinsDeuxFois"：至少结过两次婚的女人 */
    public List<Femme> findMarieesAuMoinsDeuxFois() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNamedQuery("Femme.marieesAuMoinsDeuxFois", Femme.class)
                    .getResultList();
        }
    }

    /** 查询年龄最大的女人（出生日期最早） */
    public Femme findPlusAgee() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Femme f order by f.dateNaissance asc", Femme.class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }
}
