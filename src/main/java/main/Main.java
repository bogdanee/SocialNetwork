package main;

import domain.Event;
import javafx.stage.Stage;
import repository.database.*;
import repository.memory.FriendshipRepository;
import repository.memory.UserRepository;
import service.Service;
import utils.HashingPassword;
import validator.FriendshipValidator;
import validator.UserValidator;
import window.LoginWindow;
import window.NotificationWindow;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static utils.UserPasswd.*;

public class Main
{
    public static Service service;
    public static Service getService() {
        return service;
    }


    public static void main(String[] args) throws Exception {
        UserRepository userRepo = new UserDbRepository(url, username, password);
        FriendshipRepository friendshipRepo = new FriendshipDbRepository(url, username, password);
        MessageDbRepository messageRepo = new MessageDbRepository(url, username, password);
        RequestDbRepository requestRepo = new RequestDbRepository(url, username, password);
        EventDbRepository eventRepo = new EventDbRepository(url, username, password);
        service = new Service(userRepo,friendshipRepo, messageRepo, requestRepo, eventRepo, new UserValidator(), new FriendshipValidator());
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.main(args);





    }
}