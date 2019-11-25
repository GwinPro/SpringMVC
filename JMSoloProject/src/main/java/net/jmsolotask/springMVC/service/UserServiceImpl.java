package net.jmsolotask.springMVC.service;

import net.jmsolotask.springMVC.dao.UserDao;
import net.jmsolotask.springMVC.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public List<User> getAllClient() {
        List<User> allClients = userDao.getAllUsers();
        return allClients;
    }

    @Transactional
    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        User user = userDao.getUserById(id);
        return user;
    }

    @Transactional
    @Override
    public User getUser(String name, String email) {
        User user = userDao.getUser(name, email);
        return user;
    }
}
