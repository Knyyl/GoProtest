package com.knyyl.GoProtest.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataBaseContoller {
    List<Long> keylist = new ArrayList<>();
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void removeEvent(long remKey) throws SQLException {
        try (Connection conn = getConnection()) {

            String keyCheckSql = "SELECT remKey FROM events WHERE remKey = ?";
            PreparedStatement keyCheckStatement = conn.prepareStatement(keyCheckSql);
            keyCheckStatement.setLong(1, remKey);
            ResultSet rs = keyCheckStatement.executeQuery();

            if (rs.next()) {
                long checkremKey = rs.getLong("remKey");
                if (checkremKey == remKey) {
                    String sql = "DELETE FROM events WHERE remKey = ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setLong(1, remKey);
                    int rowsDeleted = statement.executeUpdate();
                    System.out.println("Event with remKey " + remKey + " removed successfully");
                }
            } else {
                System.out.println("Incorrect Key entered: " + remKey);
            }
        }
    }


    public void addEvent(String location, String description, int attendees, long remKey) throws SQLException {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO events(location, description, attendees, remKey) VALUES(?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, location);
            statement.setString(2, description);
            statement.setInt(3, attendees);
            statement.setLong(4, remKey);
            statement.executeUpdate();
        }
    }

    public long remKeyGen() {
        long key = 0;
        key = (long) (Math.random() * 100000000000000L);
        do {
            key = (long) (Math.random() * 100_000_000_000_000L);
        } while (keylist.contains(key));
        keylist.add(key);
        return key;
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

    public void signup(int id) throws SQLException {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE events SET attendees = attendees + 1 WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

        }
    }
}


