package window;

import com.jfoenix.assets.JFoenixResources;
import domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.Service;

public class ConversationWindow extends Application {
    private final int width = 400;
    private final int height = 400;

    private Service service;
    private User sender;
    private User receiver;


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(SearchWindow.class.getResource("/fxml/conversationView.fxml"));

        AnchorPane root=loader.load();

        //ConversationWindow controller = loader.getController();


        Scene scene = new Scene(root , width, height);
        primaryStage.setScene(scene);
        primaryStage.setTitle("¯\\_(ツ)_/¯");
        primaryStage.show();
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
