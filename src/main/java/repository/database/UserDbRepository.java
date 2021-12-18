package repository.database;

import exception.RepositoryException;
import domain.User;
import repository.memory.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class UserDbRepository extends UserRepository {
    private final String url;
    private final String username;
    private final String password;

    public UserDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Add a user into the user's db
     * @param user - User
     */
    @Override
    public void add(User user) throws RepositoryException
    {
        String sql = "insert into users (first_name, last_name, username, password) values (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException("This username already exist");
        }
    }

    /**
     * Delete a user from the user's db
     * @param user - User
     * @return deleted user if the delete was successfull, null otherwise
     * @throws RepositoryException if the user doesn't exist
     */
    @Override
    public User delete(User user) {
        if (this.find(user.getId())  == null)
            throw new RepositoryException("user doesn't exist!");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?")) {
            statement.setInt(1, user.getId());
            User userDeleted = this.find(user.getId());
            statement.executeUpdate();
            return userDeleted;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Find a user from the user's db
     * @param id - integer
     * @return - the user
     *         - null if user is not in the db
     */
    @Override
    public User find(int id)
    {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?"))
        {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next())
            {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                User user = new User(firstName, lastName, username, password);
                user.setId(resultSet.getInt("id"));
                return user;
            }
            else
                return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User findByUsername(String usernameStr)
    {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username=?"))
        {

            statement.setString(1, usernameStr);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next())
            {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String password = resultSet.getString("password");

                User user = new User(firstName, lastName, usernameStr, password);
                user.setId(resultSet.getInt("id"));
                return user;
            }
            else
                return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Update a user from the user's db
     * @param user - User
     * @throws RepositoryException if the user doesn't exist
     */
    @Override
    public void update(User user)
    {
        if (this.find(user.getId())  == null)
            throw new RepositoryException("user doesn't exist!");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET first_name=?, last_name=?, username=?, password=? WHERE id=?"))
        {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Getter for users list
     * @return a list of users
     */
    @Override
    public List<User> getAll()
    {
        List<User> users = new ArrayList<User>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                User user = new User(firstName, lastName, username, password);
                user.setId(id);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
}
