package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/mysqlDB";
    private final static String username = "root";
    private final static String password = "newisland";

    public Connection connection;

    public Util() {
        try {
            connection = DriverManager.getConnection(URL, username, password);
            System.out.println(connection.isClosed());
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
