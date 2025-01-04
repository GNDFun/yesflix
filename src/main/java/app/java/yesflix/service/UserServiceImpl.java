package app.java.yesflix.service;

import app.java.yesflix.dao.UserDao;
import app.java.yesflix.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User getByPK(Long id) {
        return userDao.getByPK(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public Integer delete(Long id) {
        return userDao.delete(id);
    }

    public User getByEmail(String email){
        return userDao.getByEmail(email);
    }
}
