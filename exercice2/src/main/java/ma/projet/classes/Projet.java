package ma.projet.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

/**
 * 实体类：项目。每个项目有一个负责人（Employe），并由多个任务组成（组合关系）。
 */
@Entity
@Table(name = "projet")
public class Projet {

    // 主键，自动递增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 项目名称
    private String nom;

    // 计划开始日期
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    // 计划结束日期
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    // 项目负责人
    @ManyToOne
    @JoinColumn(name = "chef_projet_id")
    private Employe chefProjet;

    // 项目的任务（组合关系：删除项目时同时删除其任务）
    @OneToMany(mappedBy = "projet", cascade = CascadeType.REMOVE)
    private List<Tache> taches = new ArrayList<>();

    public Projet() {
    }

    public Projet(String nom, Date dateDebut, Date dateFin, Employe chefProjet) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.chefProjet = chefProjet;
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
    public Employe getChefProjet() { return chefProjet; }
    public void setChefProjet(Employe chefProjet) { this.chefProjet = chefProjet; }
    public List<Tache> getTaches() { return taches; }
    public void setTaches(List<Tache> taches) { this.taches = taches; }

    @Override
    public String toString() {
        return "Projet{id=" + id + ", nom=" + nom + ", dateDebut=" + dateDebut
                + ", dateFin=" + dateFin + "}";
    }
}
