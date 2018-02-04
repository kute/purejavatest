package com.kute.guava.eventbus.event;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * created by kute on 2018/02/04 09:51
 * 打包事件
 */
public class PackMessageEvent implements Serializable {

    private static final long serialVersionUID = 1624463644549003389L;
    private String location;

    private Timestamp date;

    private String message;

    public PackMessageEvent() {
        super();
    }

    public PackMessageEvent(String message, String location, Timestamp date) {
        this.message = message;
        this.location = location;
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PackMessageEvent{" +
                "location='" + location + '\'' +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }
}
