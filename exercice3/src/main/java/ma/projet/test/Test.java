package ma.projet.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import ma.projet.util.HibernateUtil;

/**
 * 测试程序：按照题目要求依次验证每个功能。
 */
public class Test {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

    /** 将字符串转换为日期 */
    private static Date d(String s) throws ParseException {
        return SDF.parse(s);
    }

    public static void main(String[] args) throws ParseException {
        HommeService hs = new HommeService();
        FemmeService fs = new FemmeService();
        MariageService ms = new MariageService();

        // ===== 1. 创建 10 个女人 =====
        Femme[] femmes = {
                new Femme("RAMI", "Salima", "0610000001", "Rabat", d("12/05/1970")),
                new Femme("ALI", "Amal", "0610000002", "Casablanca", d("03/02/1975")),
                new Femme("ALAOUI", "Wafa", "0610000003", "Fès", d("21/08/1978")),
                new Femme("ALAMI", "Karima", "0610000004", "Marrakech", d("15/01/1965")),
                new Femme("BENNANI", "Hind", "0610000005", "Tanger", d("30/09/1980")),
                new Femme("TAZI", "Nadia", "0610000006", "Meknès", d("11/11/1972")),
                new Femme("FASSI", "Samira", "0610000007", "Agadir", d("07/07/1968")),
                new Femme("IDRISSI", "Laila", "0610000008", "Oujda", d("19/03/1983")),
                new Femme("CHERKAOUI", "Houda", "0610000009", "Kénitra", d("25/12/1977")),
                new Femme("SQALLI", "Meryem", "0610000010", "Tétouan", d("02/06/1985"))
        };
        for (Femme f : femmes) {
            fs.create(f);
        }

        // ===== 2. 创建 5 个男人 =====
        Homme[] hommes = {
                new Homme("SAFI", "Said", "0620000001", "Rabat", d("10/10/1960")),
                new Homme("KADIRI", "Youssef", "0620000002", "Casablanca", d("05/04/1965")),
                new Homme("BERRADA", "Karim", "0620000003", "Fès", d("17/06/1970")),
                new Homme("HAJJI", "Mehdi", "0620000004", "Rabat", d("23/02/1975")),
                new Homme("NACIRI", "Omar", "0620000005", "Salé", d("09/09/1968"))
        };
        for (Homme h : hommes) {
            hs.create(h);
        }
        Homme safi = hommes[0];

        // ===== 3. 创建婚姻（SAFI SAID 与题目示例一致） =====
        ms.create(new Mariage(safi, femmes[3], d("03/09/1989"), d("03/09/1990"), 0)); // KARIMA ALAMI（已离婚）
        ms.create(new Mariage(safi, femmes[0], d("03/09/1990"), null, 4));            // SALIMA RAMI
        ms.create(new Mariage(safi, femmes[1], d("03/09/1995"), null, 2));            // AMAL ALI
        ms.create(new Mariage(safi, femmes[2], d("04/11/2000"), null, 3));            // WAFA ALAOUI
        ms.create(new Mariage(hommes[1], femmes[3], d("01/01/1992"), null, 2));       // KARIMA 第二次结婚
        ms.create(new Mariage(hommes[1], femmes[4], d("15/06/2002"), null, 1));
        ms.create(new Mariage(hommes[2], femmes[5], d("20/07/1995"), d("20/07/2000"), 1));
        ms.create(new Mariage(hommes[3], femmes[5], d("10/10/2001"), null, 2));       // NADIA 第二次结婚
        ms.create(new Mariage(hommes[3], femmes[7], d("05/05/2005"), null, 1));
        ms.create(new Mariage(hommes[4], femmes[6], d("12/12/1990"), null, 3));

        // ===== 4. 显示所有女人 =====
        System.out.println("===== Liste des femmes =====");
        fs.findAll().forEach(f -> System.out.println(f.getNomComplet() + " - née le "
                + SDF.format(f.getDateNaissance())));

        // ===== 5. 年龄最大的女人 =====
        Femme plusAgee = fs.findPlusAgee();
        System.out.println("\n===== Femme la plus âgée =====");
        System.out.println(plusAgee.getNomComplet() + " - née le " + SDF.format(plusAgee.getDateNaissance()));

        // ===== 6. 某个男人在两个日期之间的妻子 =====
        System.out.println("\n===== Épouses de " + safi.getNomComplet() + " entre 01/01/1990 et 31/12/2000 =====");
        hs.getEpousesEntreDates(safi, d("01/01/1990"), d("31/12/2000"))
                .forEach(f -> System.out.println(f.getNomComplet()));

        // ===== 7. 原生命名查询：某个女人在两个日期之间的子女数 =====
        Femme karima = femmes[3];
        System.out.println("\n===== Nombre d'enfants de " + karima.getNomComplet()
                + " entre 01/01/1980 et 31/12/2000 =====");
        System.out.println(fs.nbrEnfantsEntreDates(karima, d("01/01/1980"), d("31/12/2000")));

        // ===== 8. 命名查询：至少结过两次婚的女人 =====
        System.out.println("\n===== Femmes mariées deux fois ou plus =====");
        fs.findMarieesAuMoinsDeuxFois().forEach(f -> System.out.println(f.getNomComplet()));

        // ===== 9. Criteria API：两个日期之间娶了四个妻子的男人 =====
        System.out.println("\n===== Hommes mariés à quatre femmes entre 01/01/1989 et 31/12/2001 =====");
        System.out.println("Nombre : " + hs.countHommesMariesQuatreFemmes(d("01/01/1989"), d("31/12/2001")));
        hs.getHommesMariesQuatreFemmes(d("01/01/1989"), d("31/12/2001"))
                .forEach(h -> System.out.println(h.getNomComplet()));

        // ===== 10. 某个男人的婚姻详情（题目示例格式） =====
        System.out.println("\n===== Mariages d'un homme =====");
        hs.afficherMariages(safi);

        // 关闭会话工厂
        HibernateUtil.getSessionFactory().close();
    }
}
