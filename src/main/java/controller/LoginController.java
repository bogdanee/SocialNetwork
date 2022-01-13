package controller;

import domain.Event;
import domain.User;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import service.Service;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import utils.HashingPassword;
import window.FriendsWindow;
import window.NotificationWindow;
import window.SearchWindow;
import window.RegisterWindow;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginController {

    private Service service;
    String password;

    @FXML
    TextField textFieldUsername;

    @FXML
    TextField textFieldPassword;

    @FXML
    PasswordField passwordField;


    @FXML
    Button buttonRegister;

    @FXML
    Button buttonLogin;

    @FXML
    Label labelError;

    @FXML
    CheckBox checkBox;

    @FXML
    public void initialize() throws IOException {

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            password = newValue;
        });

        textFieldPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            password = newValue;
        });
    }

    public void handleRegister() throws Exception {
        Stage newWindow = (Stage) buttonRegister.getScene().getWindow();
        RegisterWindow register = new RegisterWindow();
        register.start(newWindow);


    }

    public void handleLogin() throws Exception {
        String username = textFieldUsername.getText();
        User user = service.findUserByUsername(username);
        if (user == null)
        {
            labelError.setText("Username doesn't exist!\nPlease register");
        }
        else if (!Objects.equals(user.getPassword(), HashingPassword.hash(password)))
        {
            labelError.setText("Incorrect password");
        }
        else
        {
            labelError.setText("");
            Stage newWindow = (Stage) buttonLogin.getScene().getWindow();
            FriendsWindow main = new FriendsWindow();
            main.setUser(user);
            main.start(newWindow);

            List<Integer> users = new ArrayList<>();
            users.add(1);
            users.add(2);
            Event event1 = new Event( 1,"Gratar1", "fain", LocalDateTime.now(), users, "");
            Event event2 = new Event( 1,"Gratar2", "fain", LocalDateTime.now(), users, "");
            Event event3 = new Event( 1,"Gratar3", "fain", LocalDateTime.now(), users, "");
            List<Event> events = new ArrayList<>();
            events.add(event1);
            events.add(event2);
            events.add(event3);
            Stage newStage = new Stage();
            HandlerNotifications handlerNotifications = new HandlerNotifications(events, (int) newWindow.getX(), (int) newWindow.getY());
            handlerNotifications.startThread();
        }


    }

    public void handleCheckBox()
    {
        if (!checkBox.isSelected())
        {
            passwordField.setText(password);
            textFieldPassword.setVisible(false);
            passwordField.setVisible(true);

        }
        else
        {
            textFieldPassword.setText(password);
            textFieldPassword.setVisible(true);
            passwordField.setVisible(false);
        }
    }

    public void setService(Service service) {
        textFieldPassword.setVisible(false);
        this.service = service;
    }
}
