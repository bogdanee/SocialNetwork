package window;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import service.Service;

import java.net.URL;
import java.util.Objects;

public class LoginWindow extends Application {
    private final int width = 400;
    private final int height = 400;


    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlLocation = getClass().getResource("/fxml/loginView.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);

        AnchorPane root=loader.load();

        LoginController controller=loader.getController();
        controller.setService(Main.getService());

        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Application");
        primaryStage.getIcons().add(new Image("/imgs/icon.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
