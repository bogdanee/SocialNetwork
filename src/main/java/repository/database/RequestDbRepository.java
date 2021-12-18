package repository.database;

import domain.FriendRequest;
import utils.RequestStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestDbRepository {
    private final String url;
    private final String username;
    private final String password;

    public RequestDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Adds a friend request to the request database
     * @param request - FriendRequest
     */
    public void add(FriendRequest request)
    {
        String sql = "insert into requests (sender, receiver, date, status) values (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, request.getId1());
            statement.setInt(2, request.getId2());
            statement.setTimestamp(3, Timestamp.valueOf(request.getDate()));
            statement.setString(4, request.getStatus().toString());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a friend request by its sender and receiver ids
     * @param idSender - int
     * @param idReceiver - int
     */
    public void delete(int idSender, int idReceiver)
    {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM requests WHERE sender=? and receiver=?")) {
            statement.setInt(1, idSender);
            statement.setInt(2, idReceiver);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Searches for a friend request based on its sender's and receiver's ids
     * @param idSender - int
     * @param idReceiver - int
     * @return the sought freind request if found, null otherwise
     */
    public FriendRequest find(int idSender, int idReceiver)
    {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM requests WHERE sender=? and receiver=?"))
        {
            statement.setInt(1, idSender);
            statement.setInt(2, idReceiver);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                FriendRequest request = new FriendRequest(idSender, idReceiver, resultSet.getTimestamp("date").toLocalDateTime());
                request.setStatus(RequestStatus.StringToStatus(resultSet.getString("status")));
                return request;
            }
            else
                return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Gets all friend requests from the database
     * @return list of friend requests
     */
    public List<FriendRequest> getAll()
    {
        List<FriendRequest> friendRequests = new ArrayList<FriendRequest>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM requests");
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next()) {
                int idSender = resultSet.getInt("sender");
                int idReceiver = resultSet.getInt("receiver");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                String status = resultSet.getString("status");
                FriendRequest friendRequest = new FriendRequest(idSender, idReceiver, date);
                friendRequest.setStatus(RequestStatus.StringToStatus(status));
                friendRequests.add(friendRequest);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return friendRequests;
    }

    /**
     * Updates a friend request with the new entity's fields
     * @param friendRequest - friendRequest
     */
    public void update(FriendRequest friendRequest)
    {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("UPDATE requests SET status=? WHERE sender=? and receiver=?"))
        {
            statement.setString(1, friendRequest.getStatus().toString());
            statement.setInt(2, friendRequest.getId1());
            statement.setInt(3, friendRequest.getId2());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
