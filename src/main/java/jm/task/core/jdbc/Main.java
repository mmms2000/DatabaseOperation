package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // implement algorithm here
        UserService userService = new UserServiceImpl();

        // Adding a User to the table.
        userService.createUsersTable();
        userService.saveUser("moses", "myat", (byte) 26);
        System.out.println("User with name - moses added to the database");

        userService.saveUser("myat", "min", (byte) 23);
        System.out.println("User with name - myat added to the database");
        userService.saveUser("soe", "aung", (byte) 28);
        System.out.println("User with name - soe added to the database");
        userService.saveUser("bob", "marly", (byte) 30);
        System.out.println("User with name - bob added to the database");

        List<User> all = userService.getAllUsers();
        if (all == null) all = Collections.emptyList(); // 안전장치 (근본 해결은 서비스/DAO에서 null 반환 금지)
        for (User user : all) {
            System.out.println(user);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
