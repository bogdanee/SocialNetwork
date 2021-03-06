package controller;

import domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import service.Service;
import window.*;


public class NavDrawerController {
    private Service service;
    private User user;

    @FXML
    Label labelName;

    @FXML
    ImageView imageUser;

    @FXML
    Button buttonProfile;

    @FXML
    Button buttonSearch;

    @FXML
    Button buttonFriends;

    @FXML
    Button buttonEvents;

    @FXML
    Button buttonLogout;

    @FXML
    public void initialize() {
    }

    public void handleProfile() throws Exception {
        Stage currentStage = (Stage) buttonProfile.getScene().getWindow();
        ProfileWindow profileWindow = new ProfileWindow();
        profileWindow.setUser(user);
        profileWindow.start(currentStage);
    }

    public void handleSearch() throws Exception
    {
        Stage currentStage = (Stage) buttonLogout.getScene().getWindow();
        SearchWindow searchWindow = new SearchWindow();
        searchWindow.setUser(user);
        searchWindow.start(currentStage);
    }

    public void handleFriends() throws Exception
    {
        Stage currentStage = (Stage) buttonLogout.getScene().getWindow();
        FriendsWindow friendsWindow = new FriendsWindow();
        friendsWindow.setUser(user);
        friendsWindow.start(currentStage);

    }

    public void handleEvents() throws Exception
    {
        Stage currentStage = (Stage) buttonLogout.getScene().getWindow();
        EventWindow eventWindow = new EventWindow();
        eventWindow.setUser(user);
        eventWindow.start(currentStage);
    }

    public void handleReports() throws Exception
    {
        //TO DO
    }

    public void handleLogout() throws Exception {
        Stage currentStage = (Stage) buttonLogout.getScene().getWindow();
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.start(currentStage);
    }

    public void setService(Service service){ this.service = service; }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLabelName(String name)
    {
        labelName.setText(name);
    }
}