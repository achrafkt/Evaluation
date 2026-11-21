package ma.projet.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

/**
 * 实体类：男人。继承自 Personne，一个男人可以有多段婚姻。
 */
@Entity
@Table(name = "homme")
public class Homme extends Personne {

    // 该男人的所有婚姻
    @OneToMany(mappedBy = "homme")
    private List<Mariage> mariages = new ArrayList<>();

    public Homme() {
    }

    public Homme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() { return mariages; }
    public void setMariages(List<Mariage> mariages) { this.mariages = mariages; }
}
