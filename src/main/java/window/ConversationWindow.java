package window;

import com.jfoenix.assets.JFoenixResources;
import controller.ConversationController;
import domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import service.Service;

import static utils.Constants.APPLICATION_HEIGHT;
import static utils.Constants.APPLICATION_WIDTH;

public class ConversationWindow extends Application {

    private Service service;
    private User sender;
    private User receiver;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(SearchWindow.class.getResource("/fxml/conversationView.fxml"));

        BorderPane root=loader.load();

        ConversationController controller = loader.getController();
        controller.setUser(sender);
        controller.setReceiver(receiver);
        controller.setService(service);
        controller.showMessages();

        Scene scene = new Scene(root , APPLICATION_WIDTH, APPLICATION_HEIGHT);
        scene.getStylesheets().add(JFoenixResources.load("/css/hamburger.css").toExternalForm());
        scene.getStylesheets().add(String.valueOf(getClass().getResource("/css/conversation.css")));
        primaryStage.setResizable(false);
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
