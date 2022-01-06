package repository.database;

import domain.ReplyMessage;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageDbRepository {
    private final String url;
    private final String username;
    private final String password;

    /**
     * Constructor
     * @param url
     * @param username
     * @param password
     */
    public MessageDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Converts a list of integers to a string concatenating them with ;
     * @param users - list of integers
     * @return the resulted string
     */
    private String parseListToString(List<Integer> users) {
        return users.stream().map(Object::toString)
                .reduce("", (x, y) -> x + y + ";");
    }

    /**
     * Adds a message (message or replyMessage) to the message's db
     * @param replyMessage - ReplyMessage
     */
    public void add(ReplyMessage replyMessage)
    {
        String sql = "insert into messages (user_id, friends, message, date, id_message_replied) values (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, replyMessage.getFrom());
            statement.setString(2, this.parseListToString(replyMessage.getTo()));
            statement.setString(3, replyMessage.getMessage());
            statement.setTimestamp(4, Timestamp.valueOf(replyMessage.getDate()));
            if (replyMessage.getIdMessageToReply() != 0)
                statement.setInt(5, (int) replyMessage.getIdMessageToReply());
            else
                statement.setObject(5, null);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a message (message or replyMessage) from messages' db
     * @param idMessage - int
     */
    public void delete(int idMessage)
    {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM messages WHERE id=?")) {
            statement.setInt(1, idMessage);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates a message from a resultSet
     * @param resultSet - resultSet
     * @return the resulted a ReplyMessage
     */
    private ReplyMessage createMessage(ResultSet resultSet)
    {
        try {
            int user_id = resultSet.getInt("user_id");
            String friends = resultSet.getString("friends");
            List<String> friendsString = List.of(friends.split(";"));
            List<Integer> friendsList = friendsString.stream().map(Integer::parseInt)
                    .collect(Collectors.toList());
            String message = resultSet.getString("message");
            LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
            int idMessageToReply = resultSet.getInt("id_message_replied");
            ReplyMessage msg = new ReplyMessage(user_id, friendsList, message, date, idMessageToReply);
            msg.setId(resultSet.getInt("id"));
            return msg;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Searches for a message by its id
     * @param id - int
     * @return the sought message if found, null otherwise
     */
    public ReplyMessage find(int id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages WHERE id=?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return this.createMessage(resultSet);
            } else
                return null;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Updates a message's fields with the given entity's fields
     * @param replyMessage - ReplyMessage
     */
    public void update(ReplyMessage replyMessage)
    {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("UPDATE messages SET friends=? WHERE id=?")) {
            statement.setString(1, this.parseListToString(replyMessage.getTo()));
            statement.setInt(2, (int) replyMessage.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns all messages from the db
     * @return list of ReplyMessages
     */
    public List<ReplyMessage> getAll() {
        List<ReplyMessage> messages = new ArrayList<ReplyMessage>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages");
             ResultSet resultSet = statement.executeQuery()) {

            while(resultSet.next())
            {
                messages.add(createMessage(resultSet));
            }
            return messages;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}