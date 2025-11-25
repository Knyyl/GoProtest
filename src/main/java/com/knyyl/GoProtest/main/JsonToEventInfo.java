package com.knyyl.GoProtest.main;

public class JsonToEventInfo {
    private long remKey;
    private String eventname;
    private String location;

    public JsonToEventInfo( long remKey, String eventname, String location){
        this.eventname = eventname;
        this.location = location;
        this.remKey = remKey;
    }

    public String getEventname() {
        return eventname;
    }
    public String getLocation(){
        return location;
    }
    public long getRemKey(){return remKey;}
    public void setRemKey(long remKey){this.remKey = remKey;}
    public void setLocation(String location){
        this.location = location;
    }
    public void setEventname(String eventname){
        this.eventname = eventname;
    }
}
