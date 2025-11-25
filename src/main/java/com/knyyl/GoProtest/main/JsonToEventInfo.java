package com.knyyl.GoProtest.main;

public class JsonToEventInfo {
    private String eventname;
    private String location;

    public JsonToEventInfo(String eventname, String location){
        this.eventname = eventname;
        this.location = location;
    }

    public String getEventname() {
        return eventname;
    }
    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setEventname(String eventname){
        this.eventname = eventname;
    }
}
