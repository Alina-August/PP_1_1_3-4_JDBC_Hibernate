package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() { }

    @Override
    public void createUsersTable() {
        final String sql =
                "CREATE TABLE IF NOT EXISTS users (" +
                        " id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        " name VARCHAR(50) NOT NULL," +
                        " lastName VARCHAR(50) NOT NULL," +
                        " age TINYINT NOT NULL" +
                        ")";
        Transaction tx = null;
        try (Session s = Util.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.createNativeQuery(sql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("createUsersTable failed", e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tx = null;
        try (Session s = Util.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("dropUsersTable failed", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try (Session s = Util.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.save(new User(name, lastName, age));
            tx.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("saveUser failed", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session s = Util.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            User u = s.get(User.class, id);
            if (u != null) s.delete(u);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("removeUserById failed", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session s = Util.getSessionFactory().openSession()) {
            return s.createQuery("from jm.task.core.jdbc.model.User", User.class).getResultList();
           // return s.createQuery("from User", User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("getAllUsers failed", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try (Session s = Util.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.createNativeQuery("DELETE FROM users").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("cleanUsersTable failed", e);
        }
    }
}