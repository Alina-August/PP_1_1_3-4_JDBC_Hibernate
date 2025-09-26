package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        User userIvan = new User("Ivan", "Ivanov", (byte) 17);
        User userAnna = new User("Anna", "Ivanova", (byte) 18);
        User userJack = new User("Jack", "Sidorov", (byte) 19);
        User userPetr = new User("Petr", "Petrov", (byte) 20);

        userService.saveUser(userIvan.getName(), userIvan.getLastName(), userIvan.getAge());
        userService.saveUser(userAnna.getName(), userAnna.getLastName(), userAnna.getAge());
        userService.saveUser(userJack.getName(), userJack.getLastName(), userJack.getAge());
        userService.saveUser(userPetr.getName(), userPetr.getLastName(), userPetr.getAge());

        List<User> users = userService.getAllUsers();
        users.forEach(user -> System.out.println(user));

        userService.cleanUsersTable();

        userService.dropUsersTable();

    }

}
