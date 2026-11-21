package ma.projet.beans;

import java.util.Date;

import jakarta.persistence.*;

/**
 * 抽象实体类：人。Homme（男）和 Femme（女）都继承自该类。
 * 使用 JOINED 继承策略：公共属性保存在 personne 表，子类各有自己的表。
 */
@Entity
@Table(name = "personne")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Personne {

    // 主键，自动递增（protected：子类可以访问）
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    // 姓
    protected String nom;

    // 名
    protected String prenom;

    // 电话
    protected String telephone;

    // 地址
    protected String adresse;

    // 出生日期
    @Temporal(TemporalType.DATE)
    protected Date dateNaissance;

    public Personne() {
    }

    public Personne(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
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
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public Date getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(Date dateNaissance) { this.dateNaissance = dateNaissance; }

    /** 返回 "姓 名"（大写），例如 "SAFI SAID" */
    public String getNomComplet() {
        return (nom + " " + prenom).toUpperCase();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", nom=" + nom + ", prenom=" + prenom
                + ", dateNaissance=" + dateNaissance + "}";
    }
}
