package ma.projet.classes;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

/**
 * 实体类：产品。每个产品属于一个类别（多对一）。
 * 命名查询 "Produit.findPrixSuperieur"：查询价格大于给定值的产品。
 */
@Entity
@Table(name = "produit")
@NamedQuery(name = "Produit.findPrixSuperieur",
        query = "from Produit p where p.prix > :prix order by p.prix")
public class Produit {

    // 主键，自动递增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 产品参考编号
    private String reference;

    // 产品价格（单位：DH）
    private float prix;

    // 所属类别
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    // 该产品出现的所有订单行
    @OneToMany(mappedBy = "produit")
    private List<LigneCommandeProduit> lignes = new ArrayList<>();

    public Produit() {
    }

    public Produit(String reference, float prix, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
    }

    // ===== getter / setter =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
    public List<LigneCommandeProduit> getLignes() { return lignes; }
    public void setLignes(List<LigneCommandeProduit> lignes) { this.lignes = lignes; }

    @Override
    public String toString() {
        return "Produit{id=" + id + ", reference=" + reference + ", prix=" + prix + "}";
    }
}
