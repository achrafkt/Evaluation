package ma.projet.classes;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * 订单行的复合主键：由产品编号和订单编号组成。
 */
@Embeddable
public class LigneCommandeProduitPK implements Serializable {

    // 产品编号
    @Column(name = "produit_id")
    private int produitId;

    // 订单编号
    @Column(name = "commande_id")
    private int commandeId;

    public LigneCommandeProduitPK() {
    }

    public LigneCommandeProduitPK(int produitId, int commandeId) {
        this.produitId = produitId;
        this.commandeId = commandeId;
    }

    public int getProduitId() { return produitId; }
    public void setProduitId(int produitId) { this.produitId = produitId; }
    public int getCommandeId() { return commandeId; }
    public void setCommandeId(int commandeId) { this.commandeId = commandeId; }

    // 复合主键必须重写 equals 和 hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LigneCommandeProduitPK)) return false;
        LigneCommandeProduitPK that = (LigneCommandeProduitPK) o;
        return produitId == that.produitId && commandeId == that.commandeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(produitId, commandeId);
    }
}
