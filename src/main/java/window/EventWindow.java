package window;

import com.jfoenix.assets.JFoenixResources;
import controller.ConversationController;
import controller.EventController;
import domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Main;
import service.Service;

import static utils.Constants.APPLICATION_HEIGHT;
import static utils.Constants.APPLICATION_WIDTH;

public class EventWindow extends Application {

    private Service service;
    private User user;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(SearchWindow.class.getResource("/fxml/eventView.fxml"));

        BorderPane root=loader.load();

        EventController controller = loader.getController();
        controller.setUser(user);
        controller.setService(Main.getService());
        controller.showEvents();

        Scene scene = new Scene(root , APPLICATION_WIDTH, APPLICATION_HEIGHT);
        scene.getStylesheets().add(JFoenixResources.load("/css/hamburger.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("¯\\_(ツ)_/¯");
        primaryStage.show();
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
