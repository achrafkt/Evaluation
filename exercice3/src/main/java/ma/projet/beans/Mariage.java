package ma.projet.beans;

import java.util.Date;

import jakarta.persistence.*;

/**
 * 关联类：婚姻。连接一个男人和一个女人，记录开始日期、结束日期和子女数量。
 * dateFin 为空表示婚姻仍在继续；不为空表示婚姻已结束（失败）。
 */
@Entity
@Table(name = "mariage")
public class Mariage {

    // 主键，自动递增（同一对夫妻可能多次结婚，因此使用独立主键）
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 结婚日期
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    // 离婚日期（为空表示婚姻仍在继续）
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    // 子女数量
    private int nbrEnfant;

    // 丈夫
    @ManyToOne
    @JoinColumn(name = "homme_id")
    private Homme homme;

    // 妻子
    @ManyToOne
    @JoinColumn(name = "femme_id")
    private Femme femme;

    public Mariage() {
    }

    public Mariage(Homme homme, Femme femme, Date dateDebut, Date dateFin, int nbrEnfant) {
        this.homme = homme;
        this.femme = femme;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrEnfant = nbrEnfant;
    }

    // ===== getter / setter =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public int getNbrEnfant() { return nbrEnfant; }
    public void setNbrEnfant(int nbrEnfant) { this.nbrEnfant = nbrEnfant; }
    public Homme getHomme() { return homme; }
    public void setHomme(Homme homme) { this.homme = homme; }
    public Femme getFemme() { return femme; }
    public void setFemme(Femme femme) { this.femme = femme; }
}
