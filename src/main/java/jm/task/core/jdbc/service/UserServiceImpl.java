package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDaoJDBC = new UserDaoJDBCImpl();

    public void createUsersTable() {
        try {
            userDaoJDBC.createUsersTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        userDaoJDBC.dropUsersTable();

    }

    public void saveUser(String name, String lastName, byte age) {
        userDaoJDBC.saveUser(name, lastName, age);
        System.out.printf("User с именем – %s добавлен в базу данных%n", name);
    }

    public void removeUserById(long id) {
        userDaoJDBC.removeUserById(id);
    }

    public List<User> getAllUsers() {
        System.out.println("getAllUsers --> Start");
        List<User> list = new ArrayList<User>(userDaoJDBC.getAllUsers());
        System.out.println("getAllUsers --> Got");
        System.out.println(list);
        System.out.println("getAllUsers --> End");
        return list;
    }

    public void cleanUsersTable() {
        userDaoJDBC.cleanUsersTable();
    }
}
