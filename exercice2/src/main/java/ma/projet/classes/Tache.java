package ma.projet.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

/**
 * 实体类：任务。每个任务属于一个项目，可以由多个员工完成。
 * 命名查询 "Tache.findPrixSuperieur"：查询价格大于给定值的任务。
 */
@Entity
@Table(name = "tache")
@NamedQuery(name = "Tache.findPrixSuperieur",
        query = "from Tache t where t.prix > :prix order by t.prix")
public class Tache {

    // 主键，自动递增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 任务名称
    private String nom;

    // 计划开始日期
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    // 计划结束日期
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    // 任务价格（单位：DH）
    private double prix;

    // 所属项目
    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    // 完成该任务的员工（关联类 EmployeTache）
    @OneToMany(mappedBy = "tache")
    private List<EmployeTache> employeTaches = new ArrayList<>();

    public Tache() {
    }

    public Tache(String nom, Date dateDebut, Date dateFin, double prix, Projet projet) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.projet = projet;
    }

    // ===== getter / setter =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public Projet getProjet() { return projet; }
    public void setProjet(Projet projet) { this.projet = projet; }
    public List<EmployeTache> getEmployeTaches() { return employeTaches; }
    public void setEmployeTaches(List<EmployeTache> employeTaches) { this.employeTaches = employeTaches; }

    @Override
    public String toString() {
        return "Tache{id=" + id + ", nom=" + nom + ", prix=" + prix + "}";
    }
}
