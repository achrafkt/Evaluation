package ma.projet.classes;

import jakarta.persistence.*;

/**
 * 关联类：订单行。表示某个订单中某个产品的数量（产品与订单之间的多对多关系）。
 */
@Entity
@Table(name = "ligne_commande_produit")
public class LigneCommandeProduit {

    // 复合主键（产品 + 订单）
    @EmbeddedId
    private LigneCommandeProduitPK id = new LigneCommandeProduitPK();

    // 订购数量
    private int quantite;

    // 关联的产品，@MapsId 表示主键中的 produitId 来自该关联
    @ManyToOne
    @MapsId("produitId")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    // 关联的订单，@MapsId 表示主键中的 commandeId 来自该关联
    @ManyToOne
    @MapsId("commandeId")
    @JoinColumn(name = "commande_id")
    private Commande commande;

    public LigneCommandeProduit() {
    }

    public LigneCommandeProduit(Produit produit, Commande commande, int quantite) {
        this.produit = produit;
        this.commande = commande;
        this.quantite = quantite;
        this.id = new LigneCommandeProduitPK(produit.getId(), commande.getId());
    }

    // ===== getter / setter =====
    public LigneCommandeProduitPK getId() { return id; }
    public void setId(LigneCommandeProduitPK id) { this.id = id; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
}
