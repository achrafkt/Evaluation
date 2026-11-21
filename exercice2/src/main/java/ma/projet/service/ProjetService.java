package ma.projet.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.AbstractDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

/**
 * 项目服务：除基本增删改查外，还可以查询项目的计划任务和已完成任务（含实际日期）。
 */
public class ProjetService extends AbstractDao<Projet> {

    public ProjetService() {
        super(Projet.class);
    }

    /** 查询某个项目计划的所有任务 */
    public List<Tache> getTachesPlanifiees(Projet projet) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from Tache t where t.projet.id = :id order by t.dateDebut", Tache.class)
                    .setParameter("id", projet.getId())
                    .getResultList();
        }
    }

    /** 显示某个项目计划的任务 */
    public void afficherTachesPlanifiees(Projet projet) {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Tâches planifiées du projet " + projet.getNom() + " :");
        System.out.printf("%-5s %-15s %-12s %-12s %-10s%n", "Num", "Nom", "Date Début", "Date Fin", "Prix");
        for (Tache t : getTachesPlanifiees(projet)) {
            System.out.printf("%-5d %-15s %-12s %-12s %-10s%n", t.getId(), t.getNom(),
                    f.format(t.getDateDebut()), f.format(t.getDateFin()), t.getPrix() + " DH");
        }
    }

    /** 查询某个项目中已完成的任务及其实际日期 */
    public List<EmployeTache> getTachesRealisees(Projet projet) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from EmployeTache et join fetch et.tache t "
                            + "where t.projet.id = :id order by et.dateDebutReelle",
                    EmployeTache.class)
                    .setParameter("id", projet.getId())
                    .getResultList();
        }
    }

    /** 按题目示例格式显示项目中已完成的任务（含实际日期） */
    public void afficherTachesRealisees(Projet projet) {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Projet : " + projet.getId() + "      Nom : " + projet.getNom()
                + "     Date début : " + formatLong(projet.getDateDebut()));
        System.out.println("Liste des tâches:");
        System.out.printf("%-5s %-15s %-18s %-18s%n", "Num", "Nom", "Date Début Réelle", "Date Fin Réelle");
        for (EmployeTache et : getTachesRealisees(projet)) {
            System.out.printf("%-5d %-15s %-18s %-18s%n", et.getTache().getId(), et.getTache().getNom(),
                    f.format(et.getDateDebutReelle()), f.format(et.getDateFinReelle()));
        }
    }

    /** 将日期格式化为 "14 Janvier 2013"（法语月份，首字母大写） */
    private static String formatLong(Date date) {
        String[] p = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).format(date).split(" ");
        return p[0] + " " + Character.toUpperCase(p[1].charAt(0)) + p[1].substring(1) + " " + p[2];
    }
}
