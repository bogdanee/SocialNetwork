package window;

import com.jfoenix.assets.JFoenixResources;
import controller.NotificationController;
import controller.ProfileController;
import domain.Event;
import domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;

import static utils.Constants.*;

public class NotificationWindow extends Application {


    private Event event;
    private double RootXValue;
    private double RootYValue;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(SearchWindow.class.getResource("/fxml/notificationView.fxml"));

        AnchorPane root=loader.load();

        NotificationController controller=loader.getController();
        controller.setEvent(event);




        Scene scene = new Scene(root , NOTIFICATION_WIDTH,  NOTIFICATION_HEIGHT);
        primaryStage.setX(RootXValue + APPLICATION_WIDTH - NOTIFICATION_WIDTH + 10);
        primaryStage.setY(RootYValue + APPLICATION_HEIGHT - NOTIFICATION_HEIGHT + 30);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("¯\\_(ツ)_/¯");
        primaryStage.show();
    }
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    public void setRootXValue(double rootXValue) {
        RootXValue = rootXValue;
    }

    public void setRootYValue(double rootYValue) {
        RootYValue = rootYValue;
    }
}
