package repository.memory;

import exception.RepositoryException;
import domain.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    protected List<User> users;

    public UserRepository() {
        users = new ArrayList<User>();
    }

    /**
     * Add a user into the user's list
     * @param user - User
     * @throws RepositoryException if the user is already exist
     */
    public void add(User user) throws SQLException {
        for (User u : users)
            if (u.getId() == user.getId())
                throw new RepositoryException("user already exist!");
        users.add(user);
    }

    /**
     * Delete a user from the user's list
     * @param user - User
     * @return deleted user
     * @throws RepositoryException if the user doesn't exist
     */
    public User delete(User user)
    {
        for (User u:users)
            if (u.getId() == user.getId()) {
                users.remove(u);
                return user;
            }
        throw new RepositoryException("user doesn't exist!");
    }

    /**
     * Find a user from the user's list
     * @param id - integer
     * @return - the user
     *         - null if user is not in the list
     */
    public User find(int id)
    {
        for (User u:users)
            if (u.getId() == id)
                return u;
        return null;
    }

    /**
     * Update a user from the user's list
     * @param user - User
     * @throws RepositoryException if the user doesn't exist
     */
    public void update(User user)
    {
        if (find(user.getId()) == null)
            throw new RepositoryException("user doesn't exist!");
        for (User u: users)
            if (u.getId() == user.getId())
                u = user;
    }

    /**
     * Getter for users list
     * @return a list of users
     */
    public List<User> getAll()
    {
        return users;
    }

    public User findByUsername(String username)
    {
        return null;
    }
}
