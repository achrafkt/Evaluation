package ma.projet.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.beans.Personne;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate 工具类：读取 application.properties 并创建唯一的 SessionFactory（单例）。
 * 启动时根据 hibernate.hbm2ddl.auto 自动生成 MySQL 数据库表。
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

            // 注册所有实体类（包括抽象父类 Personne）
            Configuration cfg = new Configuration();
            cfg.setProperties(props);
            cfg.addAnnotatedClass(Personne.class);
            cfg.addAnnotatedClass(Homme.class);
            cfg.addAnnotatedClass(Femme.class);
            cfg.addAnnotatedClass(Mariage.class);
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
