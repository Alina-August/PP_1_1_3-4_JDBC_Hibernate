package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();

        User userIvan = new User("Ivan", "Ivanov", (byte) 17);
        User userAnna = new User("Anna", "Ivanova", (byte) 18);
        User userJack = new User("Jack", "Sidorov", (byte) 19);
        User userPetr = new User("Petr", "Petrov", (byte) 20);

        userDao.saveUser(userIvan.getName(), userIvan.getLastName(), userIvan.getAge());
        userDao.saveUser(userAnna.getName(), userAnna.getLastName(), userAnna.getAge());
        userDao.saveUser(userJack.getName(), userJack.getLastName(), userJack.getAge());
        userDao.saveUser(userPetr.getName(), userPetr.getLastName(), userPetr.getAge());

        List<User> users = userDao.getAllUsers();
        users.forEach(user -> System.out.println(user));

        userDao.cleanUsersTable();

        userDao.dropUsersTable();

    }

}
