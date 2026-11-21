package ma.projet.classes;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

/**
 * 实体类：产品类别。一个类别包含多个产品（一对多）。
 */
@Entity
@Table(name = "categorie")
public class Categorie {

    // 主键，自动递增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 类别编码
    private String code;

    // 类别名称
    private String libelle;

    // 该类别下的所有产品（关系由 Produit.categorie 维护）
    @OneToMany(mappedBy = "categorie")
    private List<Produit> produits = new ArrayList<>();

    public Categorie() {
    }

    public Categorie(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    // ===== getter / setter =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public List<Produit> getProduits() { return produits; }
    public void setProduits(List<Produit> produits) { this.produits = produits; }

    @Override
    public String toString() {
        return "Categorie{id=" + id + ", code=" + code + ", libelle=" + libelle + "}";
    }
}
