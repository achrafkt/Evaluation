package ma.projet.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;
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
        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService cms = new CommandeService();
        LigneCommandeService ls = new LigneCommandeService();

        // ===== 1. 创建类别 =====
        Categorie pc = new Categorie("PC", "Ordinateurs");
        Categorie imp = new Categorie("IMP", "Imprimantes");
        cs.create(pc);
        cs.create(imp);

        // ===== 2. 创建产品 =====
        Produit p1 = new Produit("ES12", 120, pc);
        Produit p2 = new Produit("ZR85", 100, pc);
        Produit p3 = new Produit("EE85", 200, imp);
        Produit p4 = new Produit("AB10", 80, imp);
        ps.create(p1);
        ps.create(p2);
        ps.create(p3);
        ps.create(p4);

        // ===== 3. 创建订单 =====
        Commande c1 = new Commande(d("14/03/2013"));
        Commande c2 = new Commande(d("20/05/2013"));
        cms.create(c1);
        cms.create(c2);

        // ===== 4. 创建订单行 =====
        ls.create(new LigneCommandeProduit(p1, c1, 7));
        ls.create(new LigneCommandeProduit(p2, c1, 14));
        ls.create(new LigneCommandeProduit(p3, c1, 5));
        ls.create(new LigneCommandeProduit(p4, c2, 3));

        // ===== 5. 测试 findAll / findById =====
        System.out.println("===== Liste de toutes les catégories =====");
        cs.findAll().forEach(System.out::println);
        System.out.println("Produit d'id " + p1.getId() + " : " + ps.findById(p1.getId()));

        // ===== 6. 按类别显示产品 =====
        System.out.println("\n===== Produits par catégorie =====");
        ps.afficherProduitsParCategorie(pc);
        ps.afficherProduitsParCategorie(imp);

        // ===== 7. 两个日期之间被订购的产品 =====
        System.out.println("\n===== Produits commandés entre 01/01/2013 et 31/03/2013 =====");
        ps.findProduitsCommandesEntreDates(d("01/01/2013"), d("31/03/2013"))
                .forEach(System.out::println);

        // ===== 8. 显示某个订单中的产品（题目示例格式） =====
        System.out.println("\n===== Produits d'une commande =====");
        ps.afficherProduitsParCommande(c1);

        // ===== 9. 命名查询：价格大于 100 DH 的产品 =====
        System.out.println("\n===== Produits dont le prix > 100 DH =====");
        ps.findPrixSuperieur(100).forEach(System.out::println);

        // ===== 10. 测试修改与删除 =====
        p4.setPrix(90);
        ps.update(p4);
        System.out.println("\nAprès modification : " + ps.findById(p4.getId()));
        LigneCommandeProduit l = ls.findById(p4.getId(), c2.getId());
        ls.delete(l);
        System.out.println("Nombre de lignes après suppression : " + ls.findAll().size());

        // 关闭会话工厂
        HibernateUtil.getSessionFactory().close();
    }
}
