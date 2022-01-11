package main;

import repository.database.FriendshipDbRepository;
import repository.database.MessageDbRepository;
import repository.database.RequestDbRepository;
import repository.database.UserDbRepository;
import repository.memory.FriendshipRepository;
import repository.memory.UserRepository;
import service.Service;
import utils.HashingPassword;
import validator.FriendshipValidator;
import validator.UserValidator;
import window.LoginWindow;

import java.security.NoSuchAlgorithmException;

import static utils.UserPasswd.*;

public class Main
{
    public static Service service;
    public static Service getService() {
        return service;
    }


    public static void main(String[] args) {
        UserRepository userRepo = new UserDbRepository(url, username, password);
        FriendshipRepository friendshipRepo = new FriendshipDbRepository(url, username, password);
        MessageDbRepository messageRepo = new MessageDbRepository(url, username, password);
        RequestDbRepository requestRepo = new RequestDbRepository(url, username, password);
        service = new Service(userRepo,friendshipRepo, messageRepo, requestRepo, new UserValidator(), new FriendshipValidator());
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.main(args);
    }
}