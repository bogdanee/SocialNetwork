package controller;

import domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import window.LoginWindow;
import window.MainWindow;

import java.io.IOException;
import java.util.Objects;


public class NavDrawerController {

    private User user;

    @FXML
    Label labelName;

    @FXML
    Button buttonLogout;

    @FXML
    public void initialize() {

    }

    public void handleLogout() throws Exception {
        Stage newWindow = (Stage) buttonLogout.getScene().getWindow();
        LoginWindow login = new LoginWindow();


        login.start(newWindow);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLabelName(String name)
    {
        labelName.setText(name);
    }
}