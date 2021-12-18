package window;

import controller.MainController;
import controller.RegisterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;

public class MainWindow  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MainWindow.class.getResource("/fxml/mainView.fxml"));

        AnchorPane root=loader.load();

        MainController controller=loader.getController();
        controller.setService(Main.getService());
        primaryStage.setScene(new Scene(root , 700, 400));
        primaryStage.setTitle("¯\\_(ツ)_/¯");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
