package com.knyyl.GoProtest.main;

import jdk.jfr.Event;
import com.knyyl.GoProtest.main.EventLister;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseContoller {
    private final String url = "jdbc:postgresql://localhost:5432/ProtestsDB";
    private final String user = "postgres";
    private final String password = "#Ma$$mann1969";

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

    public void eventsbeau() throws SQLException {
        DataBaseContoller sql = new DataBaseContoller();
        List<EventLister> events = sql.getEvents();
        for (EventLister i : events) {
            System.out.println(i.id + " | " + i.location + " | " + i.description + " | " + i.attendees);
        }
    }

    public void countmein(int id) throws SQLException {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE events SET attendees = attendees + 1 WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

        }

    }

    public static void main(String[] args) throws SQLException, IOException {
        DataBaseContoller sql = new DataBaseContoller();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = "Hi";
        while (s != "exit") {
            System.out.println("Possible op. addevent, listevents, signup");
            System.out.println("Please enter desired operation. ");
            s = br.readLine();
            s = s.toLowerCase();
            if (s.equals("addevent")) {
                System.out.println("Enter location: ");
                String loc = br.readLine();
                System.out.println("Enter description: ");
                String desc = br.readLine();
                sql.addEvent(loc, desc, 1);
            }
            if (s.equals("listevents")) {
                sql.eventsbeau();
            }
            if (s.equals("signup")) {
                System.out.println("Please specify event by id");
                int id = Integer.parseInt(br.readLine());
                sql.countmein(id);
                System.out.println("Number of attendees has been updated.");
            }
        }
    }
}

