package com.knyyl.GoProtest.main;

public class EventLister {
    public int id;
    public int attendees;
    public String location;
    public String description;

    public EventLister(int id, String location, String description, int attendees){
        this.attendees = attendees;
        this.description = description;
        this.location = location;
        this.id = id;
    }
}
