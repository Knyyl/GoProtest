package com.knyyl.GoProtest.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private DataBaseContoller dbc;

    @PostMapping("/sendeventdetails")
    public JsonToEventInfo recieveEvent(@RequestBody JsonToEventInfo jsonToEventInfo) throws SQLException {
        dbc.addEvent(jsonToEventInfo.getEventname(), jsonToEventInfo.getLocation(), 1);
        System.out.println("Received event: " + jsonToEventInfo.getEventname() + ", location: " + jsonToEventInfo.getLocation());

        return jsonToEventInfo;
    }
}
