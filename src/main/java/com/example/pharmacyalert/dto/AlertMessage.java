package com.example.pharmacyalert.dto;

import java.io.Serializable;

public class AlertMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String topicListen;
    private String type;
    private String message;

    public AlertMessage() {
    }

    public AlertMessage(String topicListen, String type, String message) {
        this.topicListen = topicListen;
        this.type = type;
        this.message = message;
    }

    public String getTopicListen() {
        return topicListen;
    }

    public void setTopicListen(String topicListen) {
        this.topicListen = topicListen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}