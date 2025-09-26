package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS users (" +
                    " id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    " name VARCHAR(50) NOT NULL," +
                    " lastName VARCHAR(50) NOT NULL," +
                    " age TINYINT NOT NULL" +
                    ")";
    private static final String DROP_SQL = "DROP TABLE IF EXISTS users";
    private static final String INSERT_SQL = "INSERT INTO users(name, lastName, age) VALUES(?,?,?)";
    private static final String DELETE_SQL = "DELETE FROM users WHERE id=?";
    private static final String SELECT_SQL = "SELECT id, name, lastName, age FROM users";
    private static final String TRUNC_SQL = "TRUNCATE TABLE users";

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Connection c = Util.getConnection();
             Statement st = c.createStatement()) {
            st.executeUpdate(CREATE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException("createUsersTable failed", e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection c = Util.getConnection();
             Statement st = c.createStatement()) {
            st.executeUpdate(DROP_SQL);
        } catch (SQLException e) {
            throw new RuntimeException("dropUsersTable failed", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection c = Util.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT_SQL)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException("saveUser failed", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection c = Util.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE_SQL)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("removeUserById failed", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Connection c = Util.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(SELECT_SQL)) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setName(rs.getString("name"));
                u.setLastName(rs.getString("lastName"));
                u.setAge(rs.getByte("age"));
                result.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException("getAllUsers failed", e);
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection c = Util.getConnection();
             Statement st = c.createStatement()) {
            st.executeUpdate(TRUNC_SQL);
        } catch (SQLException e) {
            throw new RuntimeException("cleanUsersTable failed", e);
        }
    }
}