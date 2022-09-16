package com.hocok.eventmanager;

import java.io.Serializable;

public class Event implements Serializable {
    public String name;
    public String timeStart;
    public String timeRemind;
    public String comment;
    public String date;

    public Event(String name, String timeStart, String timeRemind, String comment, String date) {
        this.name = name;
        this.timeStart = timeStart;
        this.timeRemind = timeRemind;
        this.comment = comment;
        this.date = date;
    }
}
