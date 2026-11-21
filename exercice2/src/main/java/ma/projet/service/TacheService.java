package ma.projet.service;

import java.util.Date;
import java.util.List;

import ma.projet.classes.Tache;
import ma.projet.dao.AbstractDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

/**
 * 任务服务：除基本增删改查外，还提供按价格（命名查询）和按实际日期的查询。
 */
public class TacheService extends AbstractDao<Tache> {

    public TacheService() {
        super(Tache.class);
    }

    /** 使用命名查询 "Tache.findPrixSuperieur" 查询价格大于指定值的任务 */
    public List<Tache> findPrixSuperieur(double prix) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNamedQuery("Tache.findPrixSuperieur", Tache.class)
                    .setParameter("prix", prix)
                    .getResultList();
        }
    }

    /** 查询在两个日期之间完成的任务（实际开始和实际结束都在区间内） */
    public List<Tache> findTachesRealiseesEntreDates(Date d1, Date d2) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select distinct et.tache from EmployeTache et "
                            + "where et.dateDebutReelle >= :d1 and et.dateFinReelle <= :d2",
                    Tache.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getResultList();
        }
    }
}
