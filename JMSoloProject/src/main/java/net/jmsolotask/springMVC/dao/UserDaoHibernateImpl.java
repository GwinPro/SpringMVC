package net.jmsolotask.springMVC.dao;

import net.jmsolotask.springMVC.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoHibernateImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    @Override
    public boolean addUser(String name, String email, String phone, String role) {
        if (!hasUser(name, email)) {
            Session session = this.sessionFactory.getCurrentSession();
            User user = new User(name, email, phone, role);
            //Transaction transaction = session.beginTransaction();
            session.save(user);
            //transaction.commit();
            //session.close();
            logger.info("User successfully saved");
            return true;
        }
        return false;
    }

    @Override
    public boolean hasUser(String name, String email) {
        Session session = this.sessionFactory.getCurrentSession();
        //Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(" SELECT COUNT (*) FROM User user  WHERE user.name = :name " +
                "AND user.email = :email");
        query.setParameter("name", name);
        query.setParameter("email", email);
        Long rows = (Long) query.uniqueResult();
        //transaction.commit();
        if (rows != 0) {
            logger.info("User successfully loaded");
            return true;
        }
        logger.info("User not found");
        return false;
    }

    @Override
    public void deleteUser(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        //Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("DELETE FROM User user WHERE user.id = :id ");
        query.setParameter("id", id);
        int rows = query.executeUpdate();
        logger.info("User successfully delete");
        //transaction.commit();
        //session.close();
    }

    @Override
    public void updateUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        //Transaction transaction = session.beginTransaction();
        session.update(user);
        logger.info("User successfully update");
        //transaction.commit();
        //session.close();
    }

    @Override
    public User getUserById(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        //Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM User user WHERE user.id = :id ");
        query.setParameter("id", id);
        User user = (User) query.uniqueResult();
        logger.info("User successfully loaded");
        //transaction.commit();
        //session.close();
        return user;
    }

    @Override
    public User getUser(String name, String email) {
        Session session = this.sessionFactory.getCurrentSession();
        //Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM User user WHERE user.name = :name AND user.email = :email");
        query.setParameter("name", name);
        query.setParameter("email", email);
        User user = (User) query.uniqueResult();
        logger.info("User successfully loaded");
        //transaction.commit();
        //session.close();
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        Session session = this.sessionFactory.getCurrentSession();
        //Transaction transaction = session.beginTransaction();
        List<User> users = session.createQuery("FROM User user").list();
        //transaction.commit();
        //session.close();
        logger.info("Users list successfully loaded");
        return users;
    }
}
