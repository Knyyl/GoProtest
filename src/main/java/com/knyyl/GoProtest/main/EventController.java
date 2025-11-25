package com.knyyl.GoProtest.main;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EventController {

    @PostMapping("/sendeventdetails")
    public JsonToEventInfo recieveEvent(@RequestBody JsonToEventInfo jsonToEventInfo) throws SQLException {
        DataBaseContoller dbc = new DataBaseContoller();
        dbc.addEvent(jsonToEventInfo.getEventname(), jsonToEventInfo.getLocation(), 1);
        System.out.println("Received event: " + jsonToEventInfo.getEventname() + ", location: " + jsonToEventInfo.getLocation());

        return jsonToEventInfo;
    }
}
