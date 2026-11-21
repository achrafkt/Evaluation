package ma.projet.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate 工具类：读取 application.properties 并创建唯一的 SessionFactory（单例）。
 */
public class HibernateUtil {

    // 全局唯一的会话工厂
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try (InputStream in = HibernateUtil.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (in == null) {
                throw new IllegalStateException("找不到 application.properties 文件");
            }
            // 加载配置文件中的数据库参数
            Properties props = new Properties();
            props.load(in);

            // 注册所有实体类
            Configuration cfg = new Configuration();
            cfg.setProperties(props);
            cfg.addAnnotatedClass(Employe.class);
            cfg.addAnnotatedClass(Projet.class);
            cfg.addAnnotatedClass(Tache.class);
            cfg.addAnnotatedClass(EmployeTache.class);
            return cfg.buildSessionFactory();
        } catch (IOException e) {
            throw new ExceptionInInitializerError("读取配置文件失败：" + e.getMessage());
        }
    }

    /** 获取会话工厂 */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
