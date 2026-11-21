package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.dao.AbstractDao;

/**
 * 订单服务：提供订单的增删改查。
 */
public class CommandeService extends AbstractDao<Commande> {

    public CommandeService() {
        super(Commande.class);
    }
}
