package app.java.yesflix.service;

import app.java.yesflix.entity.User;

public interface UserService extends AbstractService<User>{
    User getByEmail(String email);
}
