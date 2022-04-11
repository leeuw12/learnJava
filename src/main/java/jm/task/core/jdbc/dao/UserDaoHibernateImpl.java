package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        Transaction tx = null;
        try (session) {
            tx = session.beginTransaction();
            util.getSession()
                    .createSQLQuery(sqlQuery)
                    .executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            try {
                if (tx != null)
                    tx.rollback();
            } catch (HibernateException e1) {
                System.out.println("Transaction roleback not succesful");
            }
            throw e;
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = util.getSession();
        Transaction tx = null;
        try (session) {
            tx = session.beginTransaction();
            util.getSession()
                    .createSQLQuery("DROP TABLE IF EXISTS user")
                    .executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            try {
                if (tx != null)
                    tx.rollback();
            } catch (HibernateException e1) {
                System.out.println("Transaction roleback not succesful");
            }
            throw e;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = util.getSession();
        Transaction tx = null;
        try (session) {
            tx = session.beginTransaction();
            util.getSession().save(new User(name, lastName, age));
            tx.commit();
        } catch (RuntimeException e) {
            try {
                if (tx != null)
                    tx.rollback();
            } catch (HibernateException e1) {
                System.out.println("Transaction roleback not succesful");
            }
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = util.getSession();
        Transaction tx = null;
        try (session) {
            User user = session.get(User.class, id);
            tx = session.beginTransaction();
            util.getSession().delete(user);
            tx.commit();
        } catch (RuntimeException e) {
            try {
                if (tx != null)
                    tx.rollback();
            } catch (HibernateException e1) {
                System.out.println("Transaction roleback not succesful");
            }
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Session session = util.getSession();
        Transaction tx = null;
        try (session) {
            tx = session.beginTransaction();
            users = util.getSession().createQuery("Select a FROM User a", User.class).getResultList();
            tx.commit();
        } catch (RuntimeException e) {
            try {
                if (tx != null)
                    tx.rollback();
            } catch (HibernateException e1) {
                System.out.println("Transaction roleback not succesful");
            }
            throw e;
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = util.getSession();
        Transaction tx = null;
        try (session) {
            tx = session.beginTransaction();
            util.getSession()
                    .createQuery("delete from User")
                    .executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            try {
                if (tx != null)
                    tx.rollback();
            } catch (HibernateException e1) {
                System.out.println("Transaction roleback not succesful");
            }
            throw e;
        }
    }
}
