package net.jmsolotask.springMVC.dao;

import net.jmsolotask.springMVC.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.LongType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoHibernateImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(user);
        logger.info("User successfully saved");
    }

    @Override
    public void deleteUser(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        //Query query = session.createQuery("DELETE FROM User user WHERE user.id = :id ");
        //query.setParameter("id", id);
        //query.executeUpdate();
        User user = getUserById(id);
        if (user != null) {
            session.delete(user);
        }
        logger.info("User successfully delete");
    }

    @Override
    public void updateUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(user);
        logger.info("User successfully update");
    }

    @Override
    public User getUserById(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        //Query query = session.createQuery("FROM User user WHERE user.id = :id ");
        //query.setParameter("id", id);
        //User user = (User) query.uniqueResult();
        User user = session.get(User.class, id);
        logger.info("User successfully loaded");
        return user;
    }

    @Override
    public User getUser(String name, String email) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM User user WHERE user.name = :name AND user.email = :email");
        query.setParameter("name", name);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        logger.info("User successfully loaded");
        return CollectionUtils.isEmpty(users) ? null : users.get(0);
    }

    @Override
    public List<User> getAllUsers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> users = session.createQuery("FROM User user").list();
        logger.info("Users list successfully loaded");
        return users;
    }
}
