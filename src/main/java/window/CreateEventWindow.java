package window;

import controller.CreateEventController;
import controller.RemoveFriendController;
import domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.Service;
import utils.Constants;

public class CreateEventWindow extends Application {

    private User user;
    private Service service;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(SearchWindow.class.getResource("/fxml/createEventView.fxml"));

        AnchorPane root=loader.load();

        CreateEventController controller = loader.getController();
        controller.setUser(user);
        controller.setService(service);

        Scene scene = new Scene(root , Constants.APPLICATION_WIDTH, Constants.APPLICATION_HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/imgs/icon.png"));
        primaryStage.setTitle("¯\\_(ツ)_/¯");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
