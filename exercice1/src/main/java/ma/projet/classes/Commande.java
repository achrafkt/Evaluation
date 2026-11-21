package ma.projet.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

/**
 * 实体类：订单。一个订单包含多个订单行（LigneCommandeProduit）。
 */
@Entity
@Table(name = "commande")
public class Commande {

    // 主键，自动递增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 订单日期（只保存日期部分）
    @Temporal(TemporalType.DATE)
    @Column(name = "date_commande")
    private Date date;

    // 订单中的所有订单行
    @OneToMany(mappedBy = "commande")
    private List<LigneCommandeProduit> lignes = new ArrayList<>();

    public Commande() {
    }

    public Commande(Date date) {
        this.date = date;
    }

    // ===== getter / setter =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public List<LigneCommandeProduit> getLignes() { return lignes; }
    public void setLignes(List<LigneCommandeProduit> lignes) { this.lignes = lignes; }

    @Override
    public String toString() {
        return "Commande{id=" + id + ", date=" + date + "}";
    }
}
