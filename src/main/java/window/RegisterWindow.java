package window;

import controller.RegisterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import service.Service;

public class RegisterWindow extends Application {
    private final int width = 400;
    private final int height = 400;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(RegisterWindow.class.getResource("/fxml/registerView.fxml"));

        AnchorPane root=loader.load();

        RegisterController controller=loader.getController();
        controller.setService(Main.getService());
        primaryStage.setScene(new Scene(root , width, height));
        primaryStage.setTitle("Register");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
