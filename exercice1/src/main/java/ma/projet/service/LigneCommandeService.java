package ma.projet.service;

import java.util.List;

import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.classes.LigneCommandeProduitPK;
import ma.projet.dao.AbstractDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

/**
 * 订单行服务：提供订单行（产品-订单关联）的增删改查。
 */
public class LigneCommandeService extends AbstractDao<LigneCommandeProduit> {

    public LigneCommandeService() {
        super(LigneCommandeProduit.class);
    }

    /**
     * 保存订单行：@MapsId 不接受游离（detached）的产品和订单，
     * 因此先在当前会话中通过 getReference 重新关联它们，再保存。
     */
    @Override
    public boolean create(LigneCommandeProduit l) {
        return executeInTransaction(session -> {
            l.setProduit(session.getReference(Produit.class, l.getProduit().getId()));
            l.setCommande(session.getReference(Commande.class, l.getCommande().getId()));
            session.persist(l);
        });
    }

    /**
     * 订单行使用复合主键，因此单个 int 主键无法定位，这里返回 null。
     * 请使用 findById(produitId, commandeId)。
     */
    @Override
    public LigneCommandeProduit findById(int id) {
        return null;
    }

    /** 根据复合主键（产品编号 + 订单编号）查找订单行 */
    public LigneCommandeProduit findById(int produitId, int commandeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(LigneCommandeProduit.class,
                    new LigneCommandeProduitPK(produitId, commandeId));
        }
    }

    /** 查询所有订单行（同时加载产品和订单，避免懒加载异常） */
    @Override
    public List<LigneCommandeProduit> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from LigneCommandeProduit l join fetch l.produit join fetch l.commande",
                    LigneCommandeProduit.class).getResultList();
        }
    }
}
