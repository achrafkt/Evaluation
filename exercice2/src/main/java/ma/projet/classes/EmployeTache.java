package ma.projet.classes;

import java.util.Date;

import jakarta.persistence.*;

/**
 * 关联类：员工-任务。记录某个员工完成某个任务的实际开始和结束日期。
 */
@Entity
@Table(name = "employe_tache")
public class EmployeTache {

    // 复合主键（员工 + 任务）
    @EmbeddedId
    private EmployeTachePK id = new EmployeTachePK();

    // 实际开始日期
    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;

    // 实际结束日期
    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    // 关联的员工，主键中的 employeId 来自该关联
    @ManyToOne
    @MapsId("employeId")
    @JoinColumn(name = "employe_id")
    private Employe employe;

    // 关联的任务，主键中的 tacheId 来自该关联
    @ManyToOne
    @MapsId("tacheId")
    @JoinColumn(name = "tache_id")
    private Tache tache;

    public EmployeTache() {
    }

    public EmployeTache(Employe employe, Tache tache, Date dateDebutReelle, Date dateFinReelle) {
        this.employe = employe;
        this.tache = tache;
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
        this.id = new EmployeTachePK(employe.getId(), tache.getId());
    }

    // ===== getter / setter =====
    public EmployeTachePK getId() { return id; }
    public void setId(EmployeTachePK id) { this.id = id; }
    public Date getDateDebutReelle() { return dateDebutReelle; }
    public void setDateDebutReelle(Date dateDebutReelle) { this.dateDebutReelle = dateDebutReelle; }
    public Date getDateFinReelle() { return dateFinReelle; }
    public void setDateFinReelle(Date dateFinReelle) { this.dateFinReelle = dateFinReelle; }
    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }
    public Tache getTache() { return tache; }
    public void setTache(Tache tache) { this.tache = tache; }
}
