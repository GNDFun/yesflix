package app.java.yesflix.web;

import app.java.yesflix.dao.UserWithThisEmailExistsException;
import app.java.yesflix.entity.UserDto;
import app.java.yesflix.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/registration")
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signup(@RequestBody UserDto userDto){
        try{
            String createdUser = registrationService.signup(userDto);
            return new ResponseEntity(createdUser, HttpStatus.OK);
        }
        catch (UserWithThisEmailExistsException emailException){
            return new ResponseEntity("User with the provided email already exists.", HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception){
            return new ResponseEntity("Server error. Please contact administrator.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
