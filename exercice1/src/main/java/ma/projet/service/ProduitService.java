package ma.projet.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.dao.AbstractDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

/**
 * 产品服务：除基本增删改查外，还提供按类别、按日期、按订单、按价格的查询。
 */
public class ProduitService extends AbstractDao<Produit> {

    public ProduitService() {
        super(Produit.class);
    }

    /** 查询某个类别下的所有产品 */
    public List<Produit> findByCategorie(Categorie categorie) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from Produit p where p.categorie.id = :id", Produit.class)
                    .setParameter("id", categorie.getId())
                    .getResultList();
        }
    }

    /** 显示某个类别下的产品列表 */
    public void afficherProduitsParCategorie(Categorie categorie) {
        System.out.println("Catégorie : " + categorie.getLibelle());
        System.out.printf("%-12s %-10s%n", "Référence", "Prix");
        for (Produit p : findByCategorie(categorie)) {
            System.out.printf("%-12s %-10s%n", p.getReference(), (int) p.getPrix() + " DH");
        }
    }

    /** 查询在两个日期之间被订购过的产品（去重） */
    public List<Produit> findProduitsCommandesEntreDates(Date d1, Date d2) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select distinct l.produit from LigneCommandeProduit l "
                            + "where l.commande.date between :d1 and :d2", Produit.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getResultList();
        }
    }

    /** 查询某个订单中的所有订单行（包含产品和数量） */
    public List<LigneCommandeProduit> findLignesByCommande(Commande commande) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from LigneCommandeProduit l join fetch l.produit "
                            + "where l.commande.id = :id", LigneCommandeProduit.class)
                    .setParameter("id", commande.getId())
                    .getResultList();
        }
    }

    /** 按题目要求的格式显示某个订单中的产品 */
    public void afficherProduitsParCommande(Commande commande) {
        // 日期格式：14 Mars 2013（月份首字母大写）
        String[] parts = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH)
                .format(commande.getDate()).split(" ");
        String date = parts[0] + " " + Character.toUpperCase(parts[1].charAt(0))
                + parts[1].substring(1) + " " + parts[2];
        System.out.println("Commande : " + commande.getId() + "     Date : " + date);
        System.out.println("Liste des produits :");
        System.out.printf("%-10s %-10s %-10s%n", "Référence", "Prix", "Quantité");
        for (LigneCommandeProduit l : findLignesByCommande(commande)) {
            System.out.printf("%-10s %-10s %-10d%n",
                    l.getProduit().getReference(),
                    (int) l.getProduit().getPrix() + " DH",
                    l.getQuantite());
        }
    }

    /** 使用命名查询 "Produit.findPrixSuperieur" 查询价格大于指定值的产品 */
    public List<Produit> findPrixSuperieur(float prix) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNamedQuery("Produit.findPrixSuperieur", Produit.class)
                    .setParameter("prix", prix)
                    .getResultList();
        }
    }
}
