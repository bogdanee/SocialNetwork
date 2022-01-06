package controller;

import domain.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import service.Service;



public class RemoveFriendController {

    private User user;
    private User friend;
    private Service service;

    @FXML
    Label labelMessage;

    @FXML
    public void initialize()
    {
    }

    public void handleNo()
    {
        Stage curentStage = (Stage) labelMessage.getScene().getWindow();
        curentStage.close();
    }

    public void handleYes()
    {
        Stage curentStage = (Stage) labelMessage.getScene().getWindow();
        service.deleteFriendship(user.getId(), friend.getId());
        curentStage.close();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFriend(User friend) {
        System.out.println(friend);
        this.friend = friend;
        labelMessage.setText("Do you want to remove "
                + friend.getLastName() + "\n" + friend.getFirstName()
                + " from friends list?");
    }

    public void setService(Service service) {
        this.service = service;
    }

}
