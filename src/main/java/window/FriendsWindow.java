package window;

import com.jfoenix.assets.JFoenixResources;
import controller.FriendsController;
import controller.SearchController;
import domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Main;

import static utils.Constants.APPLICATION_HEIGHT;
import static utils.Constants.APPLICATION_WIDTH;

public class FriendsWindow extends Application {
    private User user;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(SearchWindow.class.getResource("/fxml/friendsView.fxml"));

        BorderPane root=loader.load();

        FriendsController controller=loader.getController();
        controller.setService(Main.getService());
        controller.setUser(user);
        controller.showFriends();
        controller.showRequests();

        Scene scene = new Scene(root , APPLICATION_WIDTH, APPLICATION_HEIGHT);
        scene.getStylesheets().add(JFoenixResources.load("/css/hamburger.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
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
}
