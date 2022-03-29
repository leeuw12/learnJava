package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private String URL;
    private String username;
    private String password;
    private Connection connection;
    Properties prop = new Properties();

    public Util() {
        getDatabaseProperties();
        startConnection();
    }

    private void getDatabaseProperties() {
        try (FileInputStream file = new FileInputStream("./src/main/resources/database.properties")) {
            prop.load(file);
            URL = prop.getProperty("url") + prop.getProperty("dbName");
            username = prop.getProperty("username");
            password = prop.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startConnection() {
        try {
            connection = DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
