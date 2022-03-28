package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("John", "Fox", (byte) 12);
        userService.saveUser("Marya", "Kit", (byte) 14);
        userService.saveUser("Alex", "Jetix", (byte) 16);
        userService.saveUser("Katya", "Snake", (byte) 20);
        List<User> usersList = new ArrayList<User>(userService.getAllUsers());
        for (User user : usersList) {
            user.toString();
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
