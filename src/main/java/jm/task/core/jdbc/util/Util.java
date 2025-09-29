package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/demo_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "demo_user";
    private static final String PASS = "demo_pass";
    private static SessionFactory sessionFactory;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL driver not found", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("DB connection error", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // настройки подключения
                configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                configuration.setProperty("hibernate.connection.url", URL);
                configuration.setProperty("hibernate.connection.username", USER);
                configuration.setProperty("hibernate.connection.password", PASS);
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
                configuration.setProperty("hibernate.show_sql", "true");
                // configuration.setProperty("hibernate.hbm2ddl.auto", "update");

                // регистрируем entity
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry =
                        new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties())
                                .build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                throw new RuntimeException("Hibernate SessionFactory creation failed", e);
            }
        }
        return sessionFactory;
    }
}

