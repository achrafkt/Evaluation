package ma.projet.service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.AbstractDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

/**
 * 男人服务：除基本增删改查外，还可以查询妻子、统计娶了四个妻子的男人（Criteria API），
 * 以及显示某个男人的婚姻详情。
 */
public class HommeService extends AbstractDao<Homme> {

    private final MariageService mariageService = new MariageService();

    public HommeService() {
        super(Homme.class);
    }

    /** 查询某个男人在两个日期之间娶的妻子（按结婚日期） */
    public List<Femme> getEpousesEntreDates(Homme homme, Date d1, Date d2) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select m.femme from Mariage m where m.homme.id = :id "
                            + "and m.dateDebut between :d1 and :d2 order by m.dateDebut",
                    Femme.class)
                    .setParameter("id", homme.getId())
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getResultList();
        }
    }

    /**
     * 使用 Criteria API 查询在两个日期之间娶了四个不同妻子的男人。
     * 对应的 SQL 思路：按 homme_id 分组，having count(distinct femme_id) = 4。
     */
    public List<Homme> getHommesMariesQuatreFemmes(Date d1, Date d2) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // 第一步：找出满足条件的男人编号
            CriteriaQuery<Integer> cq = cb.createQuery(Integer.class);
            Root<Mariage> m = cq.from(Mariage.class);
            Path<Integer> hommeId = m.get("homme").get("id");
            cq.select(hommeId)
                    .where(cb.between(m.<Date>get("dateDebut"), d1, d2))
                    .groupBy(hommeId)
                    .having(cb.equal(cb.countDistinct(m.get("femme")), 4L));
            List<Integer> ids = session.createQuery(cq).getResultList();
            if (ids.isEmpty()) {
                return Collections.emptyList();
            }

            // 第二步：根据编号加载男人对象（同样使用 Criteria API）
            CriteriaQuery<Homme> hq = cb.createQuery(Homme.class);
            Root<Homme> h = hq.from(Homme.class);
            hq.select(h).where(h.get("id").in(ids));
            return session.createQuery(hq).getResultList();
        }
    }

    /** 使用 Criteria API 统计在两个日期之间娶了四个妻子的男人数量 */
    public int countHommesMariesQuatreFemmes(Date d1, Date d2) {
        return getHommesMariesQuatreFemmes(d1, d2).size();
    }

    /** 按题目示例格式显示某个男人的婚姻（进行中的婚姻 + 失败的婚姻） */
    public void afficherMariages(Homme homme) {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        List<Mariage> mariages = mariageService.findByHomme(homme);

        System.out.println("Nom : " + homme.getNomComplet());

        // 进行中的婚姻：dateFin 为空
        System.out.println("Mariages En Cours :");
        int i = 1;
        for (Mariage m : mariages) {
            if (m.getDateFin() == null) {
                System.out.printf("%d. Femme : %-15s Date Début : %s    Nbr Enfants : %d%n",
                        i++, m.getFemme().getNomComplet(), f.format(m.getDateDebut()), m.getNbrEnfant());
            }
        }

        // 失败的婚姻：dateFin 不为空
        System.out.println();
        System.out.println("Mariages échoués :");
        i = 1;
        for (Mariage m : mariages) {
            if (m.getDateFin() != null) {
                System.out.printf("%d. Femme : %-15s Date Début : %s%n",
                        i++, m.getFemme().getNomComplet(), f.format(m.getDateDebut()));
                System.out.printf("Date Fin : %s    Nbr Enfants : %d%n",
                        f.format(m.getDateFin()), m.getNbrEnfant());
            }
        }
    }
}
