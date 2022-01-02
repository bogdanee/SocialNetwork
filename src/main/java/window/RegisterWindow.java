package window;

import controller.RegisterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import service.Service;

import static utils.Constants.LOGIN_SIZE;

public class RegisterWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(RegisterWindow.class.getResource("/fxml/registerView.fxml"));

        AnchorPane root=loader.load();

        RegisterController controller=loader.getController();
        controller.setService(Main.getService());
        primaryStage.setScene(new Scene(root , LOGIN_SIZE, LOGIN_SIZE));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Register");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
