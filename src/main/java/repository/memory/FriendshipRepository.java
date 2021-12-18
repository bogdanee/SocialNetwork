package repository.memory;

import exception.RepositoryException;
import domain.Friendship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FriendshipRepository {
    protected List<Friendship> friendships;

    public FriendshipRepository() {
        this.friendships = new ArrayList<Friendship>();
    }

    /**
     * Add a friendship into the friendship's list
     * @param friendship - Friendship
     * @throws RepositoryException if the friendship already exist
     */
    public void add(Friendship friendship)
    {
        for(Friendship f:friendships)
        {
            if (Objects.equals(friendship,f))
                throw new RepositoryException ("friendship already exist!");
        }
        friendships.add(friendship);
    }

    /**
     * Delete a friendship from the friendship's list
     * @param friendship - Friendship
     * @return deleted friendship
     * @throws RepositoryException if the friendship doesn't exist
     */
    public Friendship delete(Friendship friendship)
    {
        for(Friendship f:friendships)
        {
            if (Objects.equals(friendship,f))
            {
                friendships.remove(friendship);
                return friendship;
            }
        }
        throw new RepositoryException("friendship doesn't exist");
    }

    /**
     * Find a friendship from the friendship's list
     * @param id1 - integer
     * @param id2 - integer
     * @param date - LocalDateTime
     * @return - the friendship
     *         - null if friendship is not in the list
     */
    public Friendship find(int id1, int id2)
    {
        for(Friendship f:friendships)
            if ((f.getId1()==id1 && f.getId2()==id2) || (f.getId1()==id2 && f.getId2()==id1))
                return f;
        return null;
    }

    /**
     * Getter for friendships list
     * @return a list of friendships
     */
    public List<Friendship> getAll()
    {
        return friendships;
    }
}
