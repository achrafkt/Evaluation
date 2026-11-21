package ma.projet.classes;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

/**
 * 实体类：员工。一个员工可以作为项目负责人管理多个项目，也可以参与多个任务。
 */
@Entity
@Table(name = "employe")
public class Employe {

    // 主键，自动递增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 姓
    private String nom;

    // 名
    private String prenom;

    // 电话
    private String telephone;

    // 该员工作为项目负责人（Chef de projet）管理的项目
    @OneToMany(mappedBy = "chefProjet")
    private List<Projet> projets = new ArrayList<>();

    // 该员工参与的任务（关联类 EmployeTache）
    @OneToMany(mappedBy = "employe")
    private List<EmployeTache> employeTaches = new ArrayList<>();

    public Employe() {
    }

    public Employe(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    // ===== getter / setter =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public List<Projet> getProjets() { return projets; }
    public void setProjets(List<Projet> projets) { this.projets = projets; }
    public List<EmployeTache> getEmployeTaches() { return employeTaches; }
    public void setEmployeTaches(List<EmployeTache> employeTaches) { this.employeTaches = employeTaches; }

    @Override
    public String toString() {
        return "Employe{id=" + id + ", nom=" + nom + ", prenom=" + prenom
                + ", telephone=" + telephone + "}";
    }
}
