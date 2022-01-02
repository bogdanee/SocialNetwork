package window;

import com.jfoenix.assets.JFoenixResources;
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

public class SearchWindow extends Application {

    private User user;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(SearchWindow.class.getResource("/fxml/searchView.fxml"));

        BorderPane root=loader.load();

        SearchController controller=loader.getController();
        controller.setService(Main.getService());
        controller.setUser(user);

        Scene scene = new Scene(root , APPLICATION_WIDTH, APPLICATION_HEIGHT);
        scene.getStylesheets().add(JFoenixResources.load("/css/hamburger.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
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
