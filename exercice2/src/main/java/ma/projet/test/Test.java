package ma.projet.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;
import ma.projet.util.HibernateUtil;

/**
 * 测试程序：插入测试数据并验证每个功能。
 */
public class Test {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    /** 将字符串转换为日期 */
    private static Date d(String s) throws ParseException {
        return SDF.parse(s);
    }

    public static void main(String[] args) throws ParseException {
        EmployeService es = new EmployeService();
        ProjetService ps = new ProjetService();
        TacheService ts = new TacheService();
        EmployeTacheService ets = new EmployeTacheService();

        // ===== 1. 创建员工 =====
        Employe e1 = new Employe("ALAMI", "Ahmed", "0611111111");
        Employe e2 = new Employe("RAMI", "Sara", "0622222222");
        Employe e3 = new Employe("SAFI", "Omar", "0633333333");
        es.create(e1);
        es.create(e2);
        es.create(e3);

        // ===== 2. 创建项目（e1 为负责人） =====
        Projet p1 = new Projet("Gestion de stock", d("14/01/2013"), d("30/04/2013"), e1);
        Projet p2 = new Projet("Site web", d("01/05/2013"), d("30/06/2013"), e1);
        ps.create(p1);
        ps.create(p2);

        // ===== 3. 创建任务 =====
        Tache t1 = new Tache("Analyse", d("10/02/2013"), d("20/02/2013"), 800, p1);
        Tache t2 = new Tache("Conception", d("10/03/2013"), d("15/03/2013"), 1500, p1);
        Tache t3 = new Tache("Développement", d("10/04/2013"), d("25/04/2013"), 3000, p1);
        Tache t4 = new Tache("Maquettage", d("05/05/2013"), d("15/05/2013"), 900, p2);
        ts.create(t1);
        ts.create(t2);
        ts.create(t3);
        ts.create(t4);

        // ===== 4. 记录员工完成任务的实际日期 =====
        ets.create(new EmployeTache(e2, t1, d("10/02/2013"), d("20/02/2013")));
        ets.create(new EmployeTache(e2, t2, d("10/03/2013"), d("15/03/2013")));
        ets.create(new EmployeTache(e3, t3, d("10/04/2013"), d("25/04/2013")));

        // ===== 5. 员工完成的任务 / 管理的项目 =====
        System.out.println("===== EmployeService =====");
        es.afficherTachesRealisees(e2);
        es.afficherProjetsGeres(e1);

        // ===== 6. 项目的计划任务 =====
        System.out.println("\n===== ProjetService : tâches planifiées =====");
        ps.afficherTachesPlanifiees(p1);

        // ===== 7. 项目已完成任务（题目示例格式） =====
        System.out.println("\n===== ProjetService : tâches réalisées =====");
        ps.afficherTachesRealisees(p1);

        // ===== 8. 命名查询：价格大于 1000 DH 的任务 =====
        System.out.println("\n===== Tâches dont le prix > 1000 DH =====");
        ts.findPrixSuperieur(1000).forEach(System.out::println);

        // ===== 9. 两个日期之间完成的任务 =====
        System.out.println("\n===== Tâches réalisées entre 01/02/2013 et 31/03/2013 =====");
        ts.findTachesRealiseesEntreDates(d("01/02/2013"), d("31/03/2013"))
                .forEach(System.out::println);

        // ===== 10. 测试 findAll / findById / update =====
        System.out.println("\n===== Liste des employés =====");
        es.findAll().forEach(System.out::println);
        e3.setTelephone("0699999999");
        es.update(e3);
        System.out.println("Après modification : " + es.findById(e3.getId()));

        // 关闭会话工厂
        HibernateUtil.getSessionFactory().close();
    }
}
