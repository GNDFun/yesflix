package app.java.yesflix.service;

import app.java.yesflix.dao.UserWithThisEmailExistsException;
import app.java.yesflix.entity.User;
import app.java.yesflix.entity.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;

    @Override
    public String signup(UserDto userDto) throws UserWithThisEmailExistsException {
        User user = userService.getByEmail(userDto.getEmail());
        if (user != null) {
            throw new UserWithThisEmailExistsException();
        }
        User createdUser = userService.create(convertUserToEntity(userDto));
        if (createdUser != null) {
            try{
                emailService.sendConfirmEmail(createdUser);
            }
            catch (Exception e){}
            return "User was registered successfully.";
        }
        return "Failed to create user.";
    }

    private User convertUserToEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRegistrationDatetime(LocalDateTime.now());
        return user;
    }


}
