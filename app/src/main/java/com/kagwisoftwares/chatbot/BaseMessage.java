package com.kagwisoftwares.chatbot;

public class BaseMessage {

    private String message;
    private String user;
    private int userId;

    public BaseMessage(String message, String user, int userId) {
        this.message = message;
        this.user = user;
        this.userId = userId;
    }

    public String getMessage() {return message;}
    public String getUser() {return user;}
    public int getUserId() {return userId;}

}
