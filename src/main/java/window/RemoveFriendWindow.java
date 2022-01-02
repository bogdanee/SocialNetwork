package window;

import com.jfoenix.assets.JFoenixResources;
import controller.ProfileController;
import domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class RemoveFriendWindow extends Application {
    private User user;
    private User friend;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(SearchWindow.class.getResource("/fxml/removeFriendView.fxml"));

        AnchorPane root=loader.load();


        Scene scene = new Scene(root , 250,125);
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

    public void setFriend(User friend) { this.user = friend;}
}
