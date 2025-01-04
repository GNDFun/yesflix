package app.java.yesflix.dao;

import app.java.yesflix.entity.User;

public interface UserDao extends AbstractDao<User>{
    User getByEmail(String email);
}
