package app.java.yesflix.entity;

import lombok.Data;

@Data
public class EmailMessage {
    protected String from;
    protected String to;
    protected String subject;
    protected String text;
}
