package ua.alex.web.store.service;

import ua.alex.web.store.dao.UserDao;
import ua.alex.web.store.entity.User;

import java.util.List;

public class UserService{
    private UserDao<User,Long> userDao;

    public List<User> getAll() {
        return userDao.getAll();
    }


    public User getEntityByKey(Long key) {
        return userDao.getEntityByKey(key);
    }

    public Long create(User entity) {
        return userDao.create(entity);
    }


    public boolean update(Long key, User entity) {
        return userDao.update(key, entity);
    }


    public boolean delete(Long key) {
        return userDao.delete(key);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
