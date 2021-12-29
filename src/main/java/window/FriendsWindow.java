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

public class FriendsWindow extends Application {
    private final int width = 400;
    private final int height = 400;

    private User user;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(SearchWindow.class.getResource("/fxml/friendsView.fxml"));

        BorderPane root=loader.load();

        FriendsController controller=loader.getController();
        controller.setService(Main.getService());
        controller.setUser(user);

        Scene scene = new Scene(root , width, height);
        scene.getStylesheets().add(JFoenixResources.load("/css/hamburger.css").toExternalForm());
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
