package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Util util = new Util();


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sqlQuery = "create table users (id int auto_increment," +
                " name varchar(64) not null," +
                " lastName varchar(64) not null," +
                " age int not null," +
                " constraint table_name_pk" +
                " primary key (id)" +
                ");";
        makeQuery(sqlQuery);
    }

    public void dropUsersTable() {
        String sqlQuery = "DROP TABLE users;";
        makeQuery(sqlQuery);
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlQuery = "INSERT INTO users (name, lastName, age) VALUES ('%s', '%s', %d)".formatted(name, lastName, age);
        System.out.println(sqlQuery);
        makeQuery(sqlQuery);
    }

    public void removeUserById(long id) {
        String sqlQuery = "DELETE FROM users WHERE id=%d".formatted(id);
        makeQuery(sqlQuery);
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM users";
        util.startConnection();
        try (Statement statement = util.getConnection().createStatement();) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = (byte) resultSet.getInt(4);
                User user = new User(name, lastName, age);
                user.setId((long) id);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            util.closeConnection();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sqlQuery = "DELETE FROM users";
        makeQuery(sqlQuery);
    }

    private void makeQuery(String sqlQuery) {
        util.startConnection();
        try (Statement statement = util.getConnection().createStatement();) {
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            util.closeConnection();
        }
    }
}
