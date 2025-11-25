package com.knyyl.GoProtest.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private DataBaseContoller dbc;

    @PostMapping("/removeEvent")
    public void removeEvent(@RequestBody Map<String, Object> request) throws SQLException {
        try {
            String eventIdStr = request.get("eventId").toString();
            String remKeyStr = request.get("remKey").toString();

            int eventId = Integer.parseInt(eventIdStr);
            long remKey = Long.parseLong(remKeyStr);

            dbc.removeEvent(remKey);
            System.out.println("Debug: event " + eventId + " removed with remKey: " + remKey);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing numbers: " + e.getMessage());
            throw new RuntimeException("Invalid event ID or removal key");
        }
    }

    @PostMapping("/signup")
    public void signupforevent(@RequestBody Map<String, Object> request) throws SQLException {
        try {
            String eventIdStr = request.get("eventId").toString();
            int eventId = Integer.parseInt(eventIdStr);
            dbc.signup(eventId);
            System.out.println("Debug: event " + eventId + " received a signup");
        } catch (NumberFormatException e) {
            System.out.println("Error parsing eventId: " + e.getMessage());
            throw new RuntimeException("Invalid event ID");
        }
    }
    @PostMapping("/geteventdetails")
    public List<EventLister> sendevents() throws SQLException {
        return dbc.getEvents();
    }

    @PostMapping("/sendeventdetails")
    public JsonToEventInfo recieveEvent(@RequestBody JsonToEventInfo jsonToEventInfo) throws SQLException {
        long remKey = dbc.remKeyGen();
        dbc.addEvent(jsonToEventInfo.getEventname(), jsonToEventInfo.getLocation(), 1, remKey);
        System.out.println("remKey: " + remKey + " Received event: " + jsonToEventInfo.getEventname() + ", location: " + jsonToEventInfo.getLocation());
        JsonToEventInfo response = new JsonToEventInfo(remKey, jsonToEventInfo.getEventname(), jsonToEventInfo.getLocation());
        return response;
    }
}
