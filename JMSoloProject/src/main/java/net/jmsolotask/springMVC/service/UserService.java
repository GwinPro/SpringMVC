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
public class UserService {

    private static UserService userService;

    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public static UserService getUserService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    @Transactional
    public List<User> getAllClient() {
        List<User> allClients = new ArrayList<>();
        try {
            allClients = userDao.getAllUsers();
        } catch (SQLException e) {

        }
        return allClients;
    }

    @Transactional
    public boolean addUser(String name, String email, String phone, String role) {
        boolean result = false;
        try {
            result = userDao.addUser(name, email, phone, role);
        } catch (SQLException e) {
        }
        return result;
    }

    @Transactional
    public boolean hasUser(String name, String email) {
        boolean result = false;
        if (name != "" && email != "") {
            try {
                result = userDao.hasUser(name, email);
            } catch (SQLException e) {
            }
        }
        return result;
    }

    @Transactional
    public void deleteUser(Long id) {
        try {
            userDao.deleteUser(id);
        } catch (SQLException e) {
        }
    }

    @Transactional
    public boolean updateUser(User user) {
        boolean result = false;
        try {
            userDao.updateUser(user);
            result = true;
        } catch (SQLException e) {
        }
        return result;
    }

    @Transactional
    public User getUserById(Long id) {
        User user = null;
        try {
            user = userDao.getUserById(id);
        } catch (SQLException e) {
        }
        return user;
    }

    @Transactional
    public User getUser(String name, String email) {
        User user = null;
        try {
            user = userDao.getUser(name, email);
        } catch (SQLException e) {
        }
        return user;
    }


}
