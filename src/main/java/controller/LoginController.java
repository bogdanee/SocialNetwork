package controller;

import domain.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import service.Service;
import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;
import window.MainWindow;
import window.RegisterWindow;

import java.util.Objects;

public class LoginController {

    private Service service;

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



    public void handleRegister() throws Exception {
        Stage newWindow = (Stage) buttonRegister.getScene().getWindow();
        RegisterWindow register = new RegisterWindow();
        register.start(newWindow);
    }

    public void handleLogin() throws Exception {
        String username = textFieldUsername.getText().toString();
        String password = passwordField.getText().toString();
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
            MainWindow main = new MainWindow();
            main.start(newWindow);
        }
    }

    public void handleCheckBox()
    {
        if (!checkBox.isSelected())
        {
            passwordField.setText(textFieldPassword.getText());
            textFieldPassword.setVisible(false);
            passwordField.setVisible(true);

        }
        else
        {
            textFieldPassword.setText(passwordField.getText());
            textFieldPassword.setVisible(true);
            passwordField.setVisible(false);
        }
    }

    public void setService(Service service) {
        textFieldPassword.setVisible(false);
        this.service = service;
    }
}
