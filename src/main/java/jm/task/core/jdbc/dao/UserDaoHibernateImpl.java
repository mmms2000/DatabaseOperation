package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        final String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(45) NOT NULL,
                lastName VARCHAR(45) NOT NULL,
                age TINYINT NOT NULL
            )
            """;
        executeNative(sql);
    }

    @Override
    public void dropUsersTable() {
        executeNative("DROP TABLE IF EXISTS users");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            tx.commit();
        } catch (Exception e) {
            rollback(tx);
            throw new RuntimeException("saveUser failed", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User u = session.get(User.class, id);
            if (u != null) session.remove(u);
            tx.commit();
        } catch (Exception e) {
            rollback(tx);
            throw new RuntimeException("removeUserById failed", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (var session = Util.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("getAllUsers failed", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            // HQL delete is DB-agnostic
            session.createNativeQuery("delete from users").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            rollback(tx);
            throw new RuntimeException("cleanUsersTable failed", e);
        }
    }

    // helpers
    private void executeNative(String sql) {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            rollback(tx);
            throw new RuntimeException("Native SQL failed: " + sql, e);
        }
    }

    private void rollback(Transaction tx) {
        if (tx != null) {
            try { tx.rollback(); } catch (Exception ignored) { }
        }
    }
}
