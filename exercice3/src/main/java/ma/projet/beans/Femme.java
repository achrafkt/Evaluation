package ma.projet.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

/**
 * 实体类：女人。继承自 Personne，一个女人可以有多段婚姻。
 *
 * 原生命名查询 "Femme.nbrEnfantsEntreDates"：统计某个女人在两个日期之间开始的婚姻中的子女总数。
 * 命名查询 "Femme.marieesAuMoinsDeuxFois"：查询至少结过两次婚的女人。
 */
@Entity
@Table(name = "femme")
@NamedNativeQuery(name = "Femme.nbrEnfantsEntreDates",
        query = "SELECT COALESCE(SUM(m.nbrEnfant), 0) FROM mariage m "
                + "WHERE m.femme_id = :id AND m.dateDebut BETWEEN :d1 AND :d2")
@NamedQuery(name = "Femme.marieesAuMoinsDeuxFois",
        query = "select f from Femme f where (select count(m) from Mariage m where m.femme = f) >= 2")
public class Femme extends Personne {

    // 该女人的所有婚姻
    @OneToMany(mappedBy = "femme")
    private List<Mariage> mariages = new ArrayList<>();

    public Femme() {
    }

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() { return mariages; }
    public void setMariages(List<Mariage> mariages) { this.mariages = mariages; }
}
