package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private String URL;
    private String USERNAME;
    private String PASSWORD;
    private String DRIVER;
    private Connection mySQLConnection;
    Properties prop = new Properties();

    private static SessionFactory sessionFactory;
    private static Session session;

    public Util() {
        try {
            getDatabaseProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getDatabaseProperties() throws IOException {
        try (FileInputStream file = new FileInputStream("./src/main/resources/database.properties")) {
            prop.load(file);
            URL = prop.getProperty("url") + prop.getProperty("dbName");
            USERNAME = prop.getProperty("username");
            PASSWORD = prop.getProperty("password");
            DRIVER = prop.getProperty("driver");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Connection getMySQLConnection() throws SQLException, IOException {
        if (mySQLConnection == null || mySQLConnection.isClosed()) {
            try {
                mySQLConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return mySQLConnection;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DRIVER);
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USERNAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public Session getSession() {
        if (session == null || !session.isOpen())
            session = getSessionFactory().openSession();
        return session;
    }
}
