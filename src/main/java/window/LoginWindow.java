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

public class LoginWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlLocation = getClass().getResource("/fxml/loginView.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);

        AnchorPane root=loader.load();

        LoginController controller=loader.getController();

        controller.setService(Main.getService());
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setTitle("Application");
        //primaryStage.setIconified(true);
        primaryStage.getIcons().add(new Image("/imgs/logo.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
