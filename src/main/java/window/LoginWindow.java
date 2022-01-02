package window;

import controller.LoginController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Main;

import java.io.File;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

import static utils.Constants.LOGIN_SIZE;

public class LoginWindow extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlLocation = getClass().getResource("/fxml/loginView.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);

        AnchorPane root=loader.load();

        LoginController controller=loader.getController();
        controller.setService(Main.getService());

        Scene scene = new Scene(root, LOGIN_SIZE, LOGIN_SIZE);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Application");
        primaryStage.getIcons().add(new Image("/imgs/icon.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
