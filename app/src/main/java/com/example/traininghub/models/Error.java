package com.example.traininghub.models;

public class Error {

    private String message;
    private String ActionMessage;
    private int image;

    public Error(String message, String actionMessage) {
        this.message = message;
        ActionMessage = actionMessage;
    }

    public Error(String message, String actionMessage, int image) {
        this.message = message;
        ActionMessage = actionMessage;
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getActionMessage() {
        return ActionMessage;
    }

    public void setActionMessage(String actionMessage) {
        ActionMessage = actionMessage;
    }

    public int getImage() {
        return image;
    }
}
