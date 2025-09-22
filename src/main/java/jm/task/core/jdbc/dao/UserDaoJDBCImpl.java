package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public final UserDao  userDao = new UserDaoHibernateImpl();

    @Override
    public void createUsersTable() { userDao.createUsersTable(); }

    @Override
    public void dropUsersTable() { userDao.dropUsersTable(); }

    @Override
    public void saveUser(String name, String lastName, byte age) { userDao.saveUser(name, lastName, age); }

    @Override
    public void removeUserById(long id) { userDao.removeUserById(id); }

    @Override
    public List<User> getAllUsers() { return userDao.getAllUsers(); }

    @Override
    public void cleanUsersTable() { userDao.cleanUsersTable(); }
}
