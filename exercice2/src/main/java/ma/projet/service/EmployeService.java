package ma.projet.service;

import java.util.List;

import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.AbstractDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

/**
 * 员工服务：除基本增删改查外，还可以查询员工完成的任务和管理的项目。
 */
public class EmployeService extends AbstractDao<Employe> {

    public EmployeService() {
        super(Employe.class);
    }

    /** 查询某个员工完成的所有任务（通过关联类 EmployeTache） */
    public List<Tache> getTachesRealisees(Employe employe) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select et.tache from EmployeTache et where et.employe.id = :id",
                    Tache.class)
                    .setParameter("id", employe.getId())
                    .getResultList();
        }
    }

    /** 显示某个员工完成的任务 */
    public void afficherTachesRealisees(Employe employe) {
        System.out.println("Tâches réalisées par " + employe.getNom() + " " + employe.getPrenom() + " :");
        for (Tache t : getTachesRealisees(employe)) {
            System.out.println("  - " + t.getNom() + " (" + t.getPrix() + " DH)");
        }
    }

    /** 查询某个员工作为负责人管理的所有项目 */
    public List<Projet> getProjetsGeres(Employe employe) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from Projet p where p.chefProjet.id = :id", Projet.class)
                    .setParameter("id", employe.getId())
                    .getResultList();
        }
    }

    /** 显示某个员工管理的项目 */
    public void afficherProjetsGeres(Employe employe) {
        System.out.println("Projets gérés par " + employe.getNom() + " " + employe.getPrenom() + " :");
        for (Projet p : getProjetsGeres(employe)) {
            System.out.println("  - " + p.getNom());
        }
    }
}
