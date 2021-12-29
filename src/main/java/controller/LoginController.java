package controller;

import domain.User;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import service.Service;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import window.SearchWindow;
import window.RegisterWindow;

import java.io.IOException;
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
        else if (!Objects.equals(user.getPassword(), password))
        {
            labelError.setText("Incorrect password");
        }
        else
        {
            labelError.setText("");
            Stage newWindow = (Stage) buttonLogin.getScene().getWindow();
            SearchWindow main = new SearchWindow();
            main.setUser(user);
            main.start(newWindow);
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
