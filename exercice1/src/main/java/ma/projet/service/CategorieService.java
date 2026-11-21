package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.AbstractDao;

/**
 * 类别服务：提供类别的增删改查。
 */
public class CategorieService extends AbstractDao<Categorie> {

    public CategorieService() {
        super(Categorie.class);
    }
}
