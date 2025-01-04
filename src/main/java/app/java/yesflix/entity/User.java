package app.java.yesflix.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    protected Long id;
    protected String name;
    protected String password;
    protected String email;
    protected LocalDateTime registrationDatetime;
}
