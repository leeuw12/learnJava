package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private Util util = new Util();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS user (id int auto_increment," +
                " name varchar(64) not null," +
                " lastName varchar(64) not null," +
                " age int not null," +
                " constraint table_name_pk" +
                " primary key (id)" +
                ");";
        Session session = util.getSession();
        try (session) {
            Transaction tx1 = session.beginTransaction();
            util.getSession()
                    .createSQLQuery(sqlQuery)
                    .executeUpdate();
            tx1.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = util.getSession();
        try (session) {
            Transaction tx1 = session.beginTransaction();
            util.getSession()
                    .createSQLQuery("DROP TABLE IF EXISTS user")
                    .executeUpdate();
            tx1.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = util.getSession();
        try (session) {
            Transaction tx1 = session.beginTransaction();
            util.getSession().save(new User(name, lastName, age));
            tx1.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = util.getSession();
        try (session) {
            User user = session.get(User.class, id);
            Transaction tx1 = session.beginTransaction();
            util.getSession().delete(user);
            tx1.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Session session = util.getSession();
        try (session) {
            Transaction tx1 = session.beginTransaction();
            users = (List<User>) util.getSession().createQuery("From User").list();
            tx1.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = util.getSession();
        try (session) {
            Transaction tx1 = session.beginTransaction();
            util.getSession()
                    .createQuery("delete from User")
                    .executeUpdate();
            tx1.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
