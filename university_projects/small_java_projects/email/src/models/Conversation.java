package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Conversation implements Comparable<Conversation> {
    private User toUser;
    private User fromUser;
    private ArrayList<Message> messages = new ArrayList<>();
    private LocalDate date;
    private Long id;

    public Conversation(User toUser, User fromUser, LocalDate date) {
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.date = date;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public ArrayList<Message> getMessage() {
        return messages;
    }

    public void setMessage(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @Override
    public int compareTo(Conversation o) {
        if(o.getDate().isAfter(date)){
            return 1;
        }
        else{
            if(date.isAfter(o.getDate())){
                return -1;
            }

        }
        return 0;
    }
}
