package repository.database;

import exception.RepositoryException;
import domain.Friendship;
import repository.memory.FriendshipRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FriendshipDbRepository extends FriendshipRepository {
    private final String url;
    private final String username;
    private final String password;

    public FriendshipDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Add a friendship into the friendship's db
     * @param friendship - Friendship
     */
    @Override
    public void add(Friendship friendship) {
        String sql = "insert into friendships (first_id, second_id, date) values (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, friendship.getId1());
            statement.setInt(2, friendship.getId2());
            statement.setTimestamp(3, Timestamp.valueOf(friendship.getDate()));

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Delete a friendship from the friendship's db
     * @param friendship - Friendship
     * @return deleted friendship if it is deleted successfully, null otherwise
     */
    @Override
    public Friendship delete(Friendship friendship)
    {
        if (this.find(friendship.getId1(), friendship.getId2()) == null)
            throw new RepositoryException("friendship doesn't exist!");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM friendships WHERE first_id=? and second_id=?")) {
            statement.setInt(1, friendship.getId1());
            statement.setInt(2, friendship.getId2());

            statement.executeUpdate();
            return friendship;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Find a friendship from the friendship's db
     * @param id1 - integer
     * @param id2 - integer
     * @return - the friendship
     *         - null if friendship is not in the db
     */
    @Override
    public Friendship find(int id1, int id2) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships WHERE first_id=? and second_id=?"))
        {
            statement.setInt(1, id1);
            statement.setInt(2, id2);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
                return new Friendship(id1, id2, resultSet.getTimestamp("date").toLocalDateTime());
            else
                return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Getter for friendships
     * @return a list of friendships
     */
    @Override
    public List<Friendship> getAll() {
        List<Friendship> friendships = new ArrayList<Friendship>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships");
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next()) {
                int id1 = resultSet.getInt("first_id");
                int id2 = resultSet.getInt("second_id");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                Friendship friendship = new Friendship(id1, id2, date);
                friendships.add(friendship);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return friendships;
    }
}
