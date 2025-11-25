package com.knyyl.GoProtest.main;

import jdk.jfr.Event;
import com.knyyl.GoProtest.main.EventLister;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataBaseContoller {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void addEvent(String location, String description, int attendees) throws SQLException {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO events(location, description, attendees) VALUES(?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, location);
            statement.setString(2, description);
            statement.setInt(3, attendees);
            statement.executeUpdate();
        }
    }


    public List<EventLister> getEvents() throws SQLException {
        String sql = "SELECT id, location, description, attendees FROM events";
        List<EventLister> events = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String location = rs.getString("location");
                String description = rs.getString("description");
                int attendees = rs.getInt("attendees");

                EventLister event = new EventLister(id, location, description, attendees);
                events.add(event);

            }
        }
        return events;
    }
}

