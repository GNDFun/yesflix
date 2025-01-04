package app.java.yesflix.web;

import app.java.yesflix.entity.User;
import app.java.yesflix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/rest/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<Collection<User>> getAll(){
        try{
            List<User> userList = userService.getAll();
            return new ResponseEntity(userList, HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        try{
            User user = userService.getByPK(id);
            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<User> create(@RequestBody User user){
        try{
            User createdUser = userService.create(user);
            return new ResponseEntity(createdUser, HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity("Server error. Please contact administrator.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user){
        try{
            User updatedUser = userService.update(user);
            return new ResponseEntity(updatedUser, HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity("Server error. Please contact administrator.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id){
        try{
            Integer deletedUser = userService.delete(id);
            return new ResponseEntity(deletedUser, HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity("Server error. Please contact administrator.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
