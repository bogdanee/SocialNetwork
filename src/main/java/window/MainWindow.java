package window;

import com.jfoenix.assets.JFoenixResources;
import controller.MainController;
import controller.NavDrawerController;
import controller.RegisterController;
import domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.Main;

public class MainWindow  extends Application {

    private User user;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MainWindow.class.getResource("/fxml/mainView.fxml"));

        BorderPane root=loader.load();

        MainController controller=loader.getController();
        controller.setService(Main.getService());
        controller.setUser(user);

        Scene scene = new Scene(root , 700, 400);
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
