package edu.eci.arsw.parcial.model;

import java.util.Map;

public class Message {
    private String action;
    private Map<String, Object> data;

    public Message() {}

    public Message(String action, Map<String, Object> data) {
        this.action = action;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
