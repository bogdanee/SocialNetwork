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
        if (users.size() == 1 && users.get(0) == -1)
            return "";
        return users.stream().map(Object::toString)
                .reduce("", (x, y) -> x + y + ";");
    }

    public void add(Event event) {
        String sql = "insert into events (id, organiser, name, description, date, participants, image) values (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, event.getId());
            statement.setInt(2, event.getOrganiser());
            statement.setString(3, event.getName());
            statement.setString(4, event.getDescription());
            statement.setTimestamp(5, Timestamp.valueOf(event.getDate()));
            statement.setString(6, this.parseListToString(event.getParticipants()));
            statement.setString(7, event.getImageURL());

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
            int eventId = resultSet.getInt("id");
            int organiser = resultSet.getInt("organiser");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
            String participants = resultSet.getString("participants");
            List<Integer> participantsList = new ArrayList<>();
            if (participants.equals(""))
                participantsList.add(-1);
            else
            {
                List<String> participantsString = List.of(participants.split(";"));
                participantsList = participantsString.stream().map(Integer::parseInt)
                    .collect(Collectors.toList());
            }
            String imageURL = resultSet.getString("image");
            return new Event(eventId, organiser, name, description, date, participantsList, imageURL);
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
