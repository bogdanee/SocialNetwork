package repository.database;

import domain.Event;
import domain.ReplyMessage;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventDbRepository {
    private final String url;
    private final String username;
    private final String password;

    public EventDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private String parseListToString(List<Integer> users) {
        if (users.isEmpty())
            return ";";
        return users.stream().map(Object::toString)
                .reduce("", (x, y) -> x + y + ";");
    }

    public void add(Event event) {
        String sql = "insert into events (organiser, name, description, date, participants, image) values (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, event.getOrganiser());
            statement.setString(2, event.getName());
            statement.setString(3, event.getDescription());
            statement.setTimestamp(4, Timestamp.valueOf(event.getDate()));
            statement.setString(5, this.parseListToString(event.getParticipants()));
            statement.setString(6, event.getImageURL());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int idEvent)
    {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM events WHERE id=?")) {
            statement.setInt(1, idEvent);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Event createEvent(ResultSet resultSet)
    {
        try {
            int organiser = resultSet.getInt("organiser");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
            String participants = resultSet.getString("participants");
            List<Integer> participantsList = new ArrayList<>();
            List<String> participantsString = List.of(participants.split(";"));
            participantsList = participantsString.stream().map(Integer::parseInt)
                    .collect(Collectors.toList());
            String imageURL = resultSet.getString("image");
            Event event = new Event(organiser, name, description, date, participantsList, imageURL);
            event.setId(resultSet.getInt("id"));
            return event;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Event find(int id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM events WHERE id=?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return this.createEvent(resultSet);
            } else
                return null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void update(Event event)
    {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("UPDATE events SET organiser=?, name=?, description=?, date=?, participants=?, image=? WHERE id=?")) {
            statement.setInt(1, event.getOrganiser());
            statement.setString(2, event.getName());
            statement.setString(3, event.getDescription());
            statement.setTimestamp(4, Timestamp.valueOf(event.getDate()));
            statement.setString(5, this.parseListToString(event.getParticipants()));
            statement.setString(6,event.getImageURL());
            statement.setInt (7, event.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Event> getAll() {
        List<Event> events = new ArrayList<Event>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM events");
             ResultSet resultSet = statement.executeQuery()) {

            while(resultSet.next())
            {
                events.add(createEvent(resultSet));
            }
            return events;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return events;
    }

}
