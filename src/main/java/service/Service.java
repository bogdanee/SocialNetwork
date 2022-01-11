package service;

import domain.*;
import exception.RepositoryException;
import exception.ServiceException;
import exception.ValidationException;
import graph.Graph;
import observer.Observable;
import observer.Observer;
import repository.database.MessageDbRepository;
import repository.database.RequestDbRepository;
import repository.memory.FriendshipRepository;
import repository.memory.UserRepository;
import utils.Constants;
import utils.HashingPassword;
import utils.RequestStatus;
import utils.UsersStatus;
import validator.FriendshipValidator;
import validator.UserValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Service extends Observer {
    private final FriendshipRepository friendshipRepo;
    private final UserRepository userRepo;
    private final MessageDbRepository messageRepo;
    private final RequestDbRepository requestRepo;
    private final UserValidator userVali;
    private final FriendshipValidator friendshipVali;


    public Service(UserRepository userRepo,FriendshipRepository friendshipRepo,MessageDbRepository messageRepo,
                   RequestDbRepository requestRepo, UserValidator userVali, FriendshipValidator friendshipVali) {
        this.userRepo = userRepo;
        this.friendshipRepo = friendshipRepo;
        this.messageRepo = messageRepo;
        this.requestRepo = requestRepo;
        this.userVali = userVali;
        this.friendshipVali = friendshipVali;
    }

    /**
     * Delete all user's friends by an idUser
     * @param idUser - integer >0
     */
    private void deleteFriends(int idUser)
    {
        List<Friendship> friendships = friendshipRepo.getAll();
        List<Friendship> toBeDeleted = new ArrayList<Friendship>();
        for (Friendship f:friendships)
        {
            if (f.getId1()==idUser || f.getId2()==idUser)
                toBeDeleted.add(f);
        }
        for (Friendship f:toBeDeleted)
            friendshipRepo.delete(f);
    }

    /**
     * Deletes all messages from and to a given user (removes the user from the to list if it contains
     * more than one user)
     * @param idUser - int
     */
    private void deleteMessages(int idUser) {
        var userMessages = messageRepo.getAll().stream()
                .filter(x -> x.getFrom() == idUser)
                .collect(Collectors.toList());
        userMessages.forEach(x -> messageRepo.delete((int) x.getId()));

        var friendsUserMessages = messageRepo.getAll().stream()
                .filter(x -> x.getTo().contains(idUser))
                .collect(Collectors.toList());

        for (var x:friendsUserMessages)
        {
            var listWithoutUser = x.getTo();
            listWithoutUser.remove((Integer) idUser);
            if (listWithoutUser.isEmpty())
                messageRepo.delete((int) x.getId());
            else {
                var msg = new ReplyMessage(x.getFrom(), listWithoutUser, x.getMessage(), x.getDate(), x.getIdMessageToReply());
                msg.setId(x.getId());
                messageRepo.update(msg);
            }
        }
    }

    /**
     * Deletes all friend request sent or received by a given user
     * @param idUser - int
     */
    private void deleteRequests(int idUser)
    {
        var userRequest = requestRepo.getAll().stream()
                .filter(x -> x.getId1() == idUser || x.getId2() == idUser)
                .collect(Collectors.toList());
        userRequest.forEach(x -> requestRepo.delete(x.getId1(), x.getId2()));
    }

    public void deleteRequest(int idSender, int idReceiver)
    {
        requestRepo.delete(idSender, idReceiver);
    }

    /**
     * Add a user into userRepository
     * @param firstName - String
     * @param lastName - String
     * @throws ValidationException if params are not valid
     * @throws RepositoryException if new user already exist
     */
    public void addUser(String firstName, String lastName, String username, String password) throws SQLException, NoSuchAlgorithmException {
        User user = new User(firstName, lastName, username, HashingPassword.hash(password) , Constants.imageAvatar);
        userVali.validate(user);
        userRepo.add(user);
        String userPass = "\n" + username + " " + password;
        try {
            Files.write(Paths.get("src/main/java/data/users.txt"), userPass.getBytes(), StandardOpenOption.APPEND);

        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Delete a user from userRepository
     * @param id - integer
     * @throws ValidationException if id is not valid (id<0)
     * @throws RepositoryException if user doesn't exist
     */
    public void deleteUser(int id)
    {
        User user = new User("Aa", "Bb", "Cccccccc", "Dddddddd", Constants.imageAvatar);
        user.setId(id);
        userVali.validate(user);
        userRepo.delete(user);
        deleteFriends(id);
        deleteMessages(id);
        deleteRequests(id);
    }

    /**
     * Find a user from userRepository
     * @param id - integer
     * @throws ValidationException if id is not valid (id<0)
     * @return user - if user exist
     *         none - otherwise
     */
    public User findUser(int id)
    {
        User user = new User("Aa", "Bb", "Cccccccc", "Dddddddd", Constants.imageAvatar);
        user.setId(id);
        userVali.validate(user);
        return userRepo.find(id);
    }

    public User findUserByUsername (String username)
    {
        return userRepo.findByUsername(username);
    }

    /**
     * Update a user from userRepository
     * @param firstName - String
     * @param lastName - String
     * @throws ValidationException if params are not valid
     * @throws RepositoryException if user doesn't exist
     */
    public void updateUser(String firstName, String lastName, String username, String password, String imageURL)
    {
        User user = new User(firstName, lastName,username, password, imageURL);
        user.setId(userRepo.findByUsername(username).getId());
        userVali.validate(user);
        userRepo.update(user);
    }

    /**
     * Create a friendship with params in ascending order
     * @param id1 - integer
     * @param id2 - integer
     * @param date - LocalDateTime
     * @return Friendship
     */
    private Friendship createFriendship(int id1, int id2, LocalDateTime date)
    {
        int idMin = Math.min(id1,id2);
        int idMax = Math.max(id1,id2);
        return new Friendship(idMin,idMax, date);
    }

    /**
     * Add a friendship into friendshipRepository
     * @param id1 - integer
     * @param id2 - integer
     * @param date - LocalDateTime
     * @throws ServiceException - id1 = id2
     *                          - one of the two ids doesn't belong to a user
     * @throws ValidationException if ids are not valid
     * @throws RepositoryException if the friendship already exist
     */
    public void addFriendship(int id1, int id2, LocalDateTime date)
    {
        if (id1 == id2)
            throw  new ServiceException("same user!");
        Friendship friendship = createFriendship(id1, id2, date);
        friendshipVali.validate(friendship);

        User user1 = userRepo.find(id1);
        User user2 = userRepo.find(id2);
        if (user1 == null || user2 == null)
            throw new ServiceException("user doesn't exist!");
        friendshipRepo.add(friendship);

    }

    /**
     * Delete a friendship from friendshipRepository
     * @param id1 - integer
     * @param id2 - integer
     * @throws ValidationException if ids are not valid
     * @throws RepositoryException if the friendship doesn't exist
     */
    public void deleteFriendship(int id1, int id2)
    {
        Friendship friendship = createFriendship(id1, id2, LocalDateTime.now());
        friendshipVali.validate(friendship);
        friendshipRepo.delete(friendship);
    }

    /**
     * Find a friendship from friendshipRepository
     * @param id1 - integer
     * @param id2 - integer
     * @return friendship - if exist
     *         none - otherwise
     */
    public Friendship findFriendship(int id1, int id2)
    {
        Friendship friendship = createFriendship(id1, id2, LocalDateTime.now());
        friendshipVali.validate(friendship);
        return friendshipRepo.find(friendship.getId1(), friendship.getId2());
    }

    /**
     * Determines a user's friends list added in a given month
     * @param user - User
     * @param month - int
     * @return a list of users
     */
    public List<Friendship> friendsListMonth(User user, int month)
    {
        List<Friendship> friendships = friendshipRepo.getAll();
        return friendships.stream()
                .filter(x-> x.getId1()==user.getId() || x.getId2()==user.getId())
                .filter(x->x.getDate().getMonth().getValue() == month)
                .collect(Collectors.toList());
    }

    /**
     * Determines a user's friends list
     * @param user - User
     * @return a list of users
     */
    public List<Friendship> friendsList(User user)
    {
        List<Friendship> friendships = friendshipRepo.getAll();
        return friendships.stream()
                .filter(x-> x.getId1()==user.getId() || x.getId2()==user.getId())
                .collect(Collectors.toList());
    }

    /**
     * Getter for the list of all users
     * @return list of all users
     */
    public List<User> getAllUsers()
    {
        return userRepo.getAll();
    }

    /**
     * Getter for the list of all friendships
     * @return list of all friendships
     */
    public List<Friendship> getAllFriendships() { return friendshipRepo.getAll();}

    /**
     * Determine user communities based on their friendships
     * @return a map with the key as a number of community and the values as a list of users from that community
     */
    public Map<Integer, List<User>> determinateCommunities()
    {
        Graph graph = new Graph(getAllUsers(), getAllFriendships());
        int[] community = graph.getConexList();
        Map<Integer, List<User>> communityList = new HashMap<Integer, List<User>>();
        for(int i=0;i<community.length;i++) {
            communityList.computeIfAbsent(community[i], k -> new ArrayList<User>());
            communityList.get(community[i]).add(userRepo.getAll().get(i));
        }
        return communityList;
    }

    /**
     * Adds a message given its sender's id, list of receivers' ids, message, date, another message's id
     * (if it is a reply message)
     * @param idUser - int
     * @param idsFriends - List<Integer>
     * @param message - String
     * @param date - LocalDateTime
     * @param idMessageToReply - int
     */
    public void addMessage(int idUser, List<Integer> idsFriends, String message, LocalDateTime date, int idMessageToReply)
    {
        if (message.isEmpty())
            throw new ValidationException("message invalid!");
        var msg = new ReplyMessage(idUser, idsFriends, message, date, idMessageToReply);
        messageRepo.add(msg);
    }

    /**
     * Searches for a message by its id
     * @param id - int
     * @return the sought message if found, null otherwise
     */
    public ReplyMessage findMessage (int id)
    {
        return messageRepo.find(id);
    }

    /**
     * Gets a list of all the messages between 2 users identified by their ids
     * @param id1 - int
     * @param id2 - int
     * @return list of messages
     */
    public List<ReplyMessage> getAllMessageFrom2Users(int id1, int id2)
    {
        var messages = messageRepo.getAll();
        Predicate<ReplyMessage> esteOriNuIeste = (x -> (x.getTo().contains(id1)  && x.getFrom() == id2) ||
                (x.getTo().contains(id2)  && x.getFrom() == id1));

        return messages.stream()
                .filter(esteOriNuIeste)
                //.sorted(Comparator.comparing(Message::getDate))
                .collect(Collectors.toList());
    }

    /**
     * Adds a friend request given its sender's and receiver's ids and a date
     * @param senderId - int
     * @param receiverId - int
     * @param date - LocalDateTime
     */
    public void sendFriendRequest(int senderId, int receiverId, LocalDateTime date)
    {
        User userSender = userRepo.find(senderId);
        User userReceiver = userRepo.find(receiverId);
        if (userSender == null || userReceiver == null)
            throw new ServiceException("user doesn't exist!");
        Friendship fr = createFriendship(senderId, receiverId, date);
        if (friendshipRepo.find(fr.getId1(), fr.getId2()) != null)
            throw new ServiceException("already friends!");
        FriendRequest friendRequest = new FriendRequest(senderId, receiverId, date);
        FriendRequest existingRequest = requestRepo.find(receiverId, senderId);
        if (existingRequest != null && existingRequest.getStatus() == RequestStatus.PENDING)
        {
            existingRequest.setStatus(RequestStatus.APPROVED);
            requestRepo.update(existingRequest);
            friendRequest.setStatus(RequestStatus.APPROVED);
            friendshipRepo.add(createFriendship(senderId, receiverId, date));
        }
        requestRepo.add(friendRequest);
    }

    public FriendRequest findRequest(int idSender, int idReceiver)
    {
        return requestRepo.find(idSender, idReceiver);
    }

    /**
     * Gets all the requests sent and received by a given user
     * @param idUser - int
     * @return list of friend requests
     */
    public List<FriendRequest> getRequests(int idUser)
    {
        User user = userRepo.find(idUser);
        if (user == null)
            throw new ServiceException("user doesn't exist!");
        var friendRequests = requestRepo.getAll();
        return friendRequests.stream()
                .filter(x->x.getId2() == idUser && x.getStatus() == RequestStatus.PENDING)
                .collect(Collectors.toList());
    }

    /**
     * Updates a request's status with the given status (approved or rejected)
     * @param idSender - int
     * @param idReceiver - int
     * @param date - LocalDateTime
     * @param status - String
     */
    public void updateRequest(int idSender, int idReceiver, LocalDateTime date, String status)
    {
        FriendRequest request = new FriendRequest(idSender,idReceiver, date);
        request.setStatus(RequestStatus.StringToStatus(status));
        if (Objects.equals(status, "APPROVED"))
            friendshipRepo.add(createFriendship(idSender, idReceiver, date));
        requestRepo.update(request);
    }

    public String getStatus (User loggedUser, User searchedUser)
    {
        if (this.findFriendship(loggedUser.getId(), searchedUser.getId()) != null)
            return UsersStatus.FRIENDS.toString();
        List<FriendRequest> friendRequests = requestRepo.getAll();
        for (FriendRequest friendRequest : friendRequests)
        {
            if (friendRequest.getId1() == loggedUser.getId() && friendRequest.getId2() == searchedUser.getId() && friendRequest.getStatus() == RequestStatus.PENDING)
                return UsersStatus.REQUESTSENT.toString();
            else if (friendRequest.getId2() == loggedUser.getId() && friendRequest.getId1() == searchedUser.getId() && friendRequest.getStatus() == RequestStatus.PENDING)
                return UsersStatus.REQUESTRECEIVED.toString();
            else if (friendRequest.getId1() == loggedUser.getId() && friendRequest.getId2() == searchedUser.getId() && friendRequest.getStatus() == RequestStatus.REJECTED)
                return UsersStatus.REJECTED.toString();
        }
        return UsersStatus.NOTFRIENDS.toString();
    }


    @Override
    public void notifyObservables() {
        this.observables.forEach(Observable::update);
    }

    public void addObservable(Observable observable)
    {
        this.observables.add(observable);
    }
}
