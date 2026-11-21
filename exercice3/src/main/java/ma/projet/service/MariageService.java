package ma.projet.service;

import java.util.List;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.AbstractDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

/**
 * 婚姻服务：提供婚姻的增删改查。
 */
public class MariageService extends AbstractDao<Mariage> {

    public MariageService() {
        super(Mariage.class);
    }

    /** 查询某个男人的所有婚姻（按结婚日期排序） */
    public List<Mariage> findByHomme(Homme homme) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from Mariage m join fetch m.femme where m.homme.id = :id order by m.dateDebut",
                    Mariage.class)
                    .setParameter("id", homme.getId())
                    .getResultList();
        }
    }
}
