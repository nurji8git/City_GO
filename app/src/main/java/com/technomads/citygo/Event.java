package com.technomads.citygo;

public class Event {
    public String event_title, event_time, event_desc;
    public int id;

    public Event() {}
    public Event(String event_title, String event_time, String desc, int id)
    {
        this.id = id;
        this.event_title = event_title;
        this.event_time = event_time;
        this.event_desc = desc;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
