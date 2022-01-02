package controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import service.Service;

import java.io.IOException;

public class MainController {
    protected Service service;
    protected NavDrawerController navDrawerController;
    protected User user;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;


    public void handleHamburger()
    {
        drawer.toggle();
        navDrawerController.setLabelName("Welcome,\n" + user.getLastName() + " " + user.getFirstName() + "!");
        navDrawerController.imageUser.setImage(new Image(user.getImageURL()));
    }

    protected void setDrawer() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/navDrawerView.fxml"));
        VBox box = loader.load();
        navDrawerController =  loader.getController();

        drawer.setSidePane(box);
        drawer.setMinWidth(0);


        box.setStyle("-fx-opacity: 0.8; -fx-background-color: black");

        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);

        drawer.setOnDrawerOpening((event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            drawer.setMinWidth(220);
        });
        drawer.setOnDrawerClosed((event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            drawer.setMinWidth(0);
        });
    }


    public void setService(Service service)
    {
        this.service = service;
        navDrawerController.setUser(user);
    }

    public void setUser(User user) throws IOException {
        this.user = user;
        navDrawerController.setUser(user);
    }
}
