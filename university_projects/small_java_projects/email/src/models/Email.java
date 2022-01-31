package models;

import java.time.LocalDateTime;

public class Email implements Comparable<Email> {
    private User fromUser;
    private User toUser;
    private String subject="";
    private String message="";
    private LocalDateTime time;
    private Long id;

    public Email(User fromUser, User toUser, String subject, String message ) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.subject = subject;
        this.message = message;

    }

    public Email() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public int compareTo(Email o) {
        if(o.getTime().isAfter(time)){
            return 1;
        }
        if(!o.getTime().isAfter(time)){
            return -1;
        }
        return 0;
    }
}
