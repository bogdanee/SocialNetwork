package controller;

import domain.FriendRequest;
import domain.User;
import domain.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.Constants;
import utils.RequestStatus;
import utils.UsersStatus;
import window.ConversationWindow;
import window.RemoveFriendWindow;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FriendsController extends MainController {
    ObservableList<UserDTO> friendsList = FXCollections.observableArrayList();
    ObservableList<UserDTO> requestsList = FXCollections.observableArrayList();

    @FXML
    ListView<UserDTO> listViewFriends;

    @FXML
    ListView<UserDTO> listViewRequests;


    @FXML
    public void initialize() throws IOException {
        listViewFriends.setItems(friendsList);
        listViewRequests.setItems(requestsList);
        setFriends();
        setRequests();
        super.setDrawer();
    }

    private void setFriends()
    {
        var cellFactory = new Callback<ListView<UserDTO>, ListCell<UserDTO>>() {
            @Override
            public ListCell<UserDTO> call(ListView<UserDTO> param) {
                ListCell<UserDTO> cell = new ListCell<UserDTO>() {
                    private final AnchorPane anchorPane = new AnchorPane();
                    private final ImageView image = new ImageView();
                    private final Label labelName = new Label();

                    private final VBox vbox = new VBox();
                    private final Button buttonMessage = new Button("Send message");
                    private final Button buttonRemoveFriend = new Button("Remove friend");
                    
                    {
                        this.setStyle("-fx-background-color: #000000; -fx-font-size:15;");
                        labelName.setStyle("-fx-text-fill: white; ");
                        buttonMessage.setStyle("-fx-background-color:#248CF0FF; -fx-text-fill: white; -fx-font-size: 10");
                        buttonRemoveFriend.setStyle("-fx-background-color:red; -fx-text-fill: black; -fx-font-size: 10");
                        vbox.setSpacing(5);

                        image.setFitWidth(35d);
                        image.setFitHeight(35d);

                        AnchorPane.setTopAnchor(image, 3d);
                        anchorPane.getChildren().add(image);

                        AnchorPane.setLeftAnchor(labelName, 40d);
                        anchorPane.getChildren().add(labelName);

                        vbox.getChildren().addAll(buttonMessage,buttonRemoveFriend);
                        AnchorPane.setRightAnchor(vbox, 1d);
                        anchorPane.getChildren().add(vbox);
                    }

                    @Override
                    public void updateItem(UserDTO item, boolean empty) {

                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            labelName.setText(item.getName());
                            image.setImage(new Image(item.getImageURL()));

                            buttonMessage.setOnAction((ActionEvent event) -> {
                                try {
                                    showConversation(user, service.findUser(item.getId()));
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            });
                            buttonRemoveFriend.setOnAction((ActionEvent event) ->{
                                try {
                                    handleRemoveFriend(item);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                            setGraphic(anchorPane);
                        }
                    }
                };
                return cell;
            }
        };
        listViewFriends.setCellFactory(cellFactory);
    }

    private void handleRemoveFriend(UserDTO friend) throws Exception {
        Stage currentStage = (Stage) listViewFriends.getScene().getWindow();
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(currentStage);

        RemoveFriendWindow removeFriendWindow = new RemoveFriendWindow();
        removeFriendWindow.setUser(user);
        System.out.println(service.findUser(friend.getId()));
        removeFriendWindow.setFriend(service.findUser(friend.getId()));
        removeFriendWindow.setService(service);
        removeFriendWindow.start(newWindow);
    }

    private void setRequests()
    {
        var cellFactory = new Callback<ListView<UserDTO>, ListCell<UserDTO>>() {
            @Override
            public ListCell<UserDTO> call(ListView<UserDTO> param) {
                ListCell<UserDTO> cell = new ListCell<UserDTO>() {
                    private final AnchorPane anchorPane = new AnchorPane();
                    private final ImageView image = new ImageView();
                    private final Label labelName = new Label();

                    private final VBox vBox = new VBox();
                    private final Label labelDate = new Label();
                    private final HBox hbox = new HBox();
                    private final Button buttonAccept = new Button("Accept");
                    private final Button buttonDeny = new Button("Deny");

                    {
                        this.setStyle("-fx-background-color: #000000; -fx-font-size:15;");
                        labelName.setStyle("-fx-text-fill: white; ");
                        labelDate.setStyle("-fx-text-fill: white; -fx-font-size: 10;");
                        buttonAccept.setStyle("-fx-background-color:#248CF0FF; -fx-text-fill: white; -fx-font-size: 10");
                        buttonDeny.setStyle("-fx-background-color:white; -fx-text-fill: black; -fx-font-size: 10");
                        vBox.setSpacing(5d);
                        vBox.setAlignment(Pos.CENTER);
                        hbox.setSpacing(5d);


                        image.setFitWidth(35d);
                        image.setFitHeight(35d);

                        AnchorPane.setTopAnchor(image, 3d);
                        anchorPane.getChildren().add(image);

                        AnchorPane.setLeftAnchor(labelName, 40d);
                        anchorPane.getChildren().add(labelName);

                        hbox.getChildren().addAll(buttonAccept, buttonDeny);
                        vBox.getChildren().addAll(labelDate, hbox);
                        AnchorPane.setRightAnchor(vBox, 1d);
                        anchorPane.getChildren().add(vBox);
                    }

                    @Override
                    public void updateItem(UserDTO item, boolean empty) {

                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            labelName.setText(item.getName());
                            image.setImage(new Image(item.getImageURL()));

                            var dateRequest = service.findRequest(item.getId(), user.getId()).getDate().format(Constants.DATE_TIME_FORMATTER);
                            labelDate.setText(dateRequest);

                            buttonAccept.setOnAction((ActionEvent event) -> {
                                service.updateRequest(item.getId(), user.getId(), LocalDateTime.now(), RequestStatus.APPROVED.toString());
                                item.setStatus(UsersStatus.FRIENDS.toString());
                                showFriends();
                                showRequests();
                            });
                            buttonDeny.setOnAction((ActionEvent event) -> {
                                service.deleteRequest(item.getId(), user.getId());
                                item.setStatus(UsersStatus.REJECTED.toString());
                                showRequests();

                            });
                            setGraphic(anchorPane);
                        }
                    }
                };
                return cell;
            }
        };
        listViewRequests.setCellFactory(cellFactory);
    }

    private void showConversation(User sender, User receiver) throws Exception {


        Stage currentStage = (Stage) listViewFriends.getScene().getWindow();
        ConversationWindow conversationWindow = new ConversationWindow();
        conversationWindow.setSender(sender);
        conversationWindow.setReceiver(receiver);
        conversationWindow.setService(service);
        conversationWindow.start(currentStage);
    }

    private List<UserDTO> getFriendsList()
    {
        List<User> usersList = service.getAllUsers();
        Map<Integer, User> userMap = new HashMap<>();
        for (User user: usersList)
        {
            userMap.put(user.getId(), user);
        }
        return service.friendsList(user).stream()
                .map(f ->{
                    if (user.getId() == f.getId1())
                        return userMap.get(f.getId2());
                    else
                        return userMap.get(f.getId1());

                })
                .map(u -> new UserDTO(u.getId(), u.getLastName() + " " + u.getFirstName(), service.getStatus(user, u), u.getImageURL()))
                .collect(Collectors.toList());
    }

    public void showFriends()
    {
        friendsList.setAll(getFriendsList());
        listViewFriends.setVisible(!friendsList.isEmpty());
    }

    private List<UserDTO> getRequestsList()
    {
        List<FriendRequest> requests = service.getRequests(user.getId());
        return requests.stream()
                .filter(f -> f.getId2() == user.getId())
                .map(f -> {
                    User sender = service.findUser(f.getId1());
                    return new UserDTO(sender.getId(), sender.getLastName() + " " + sender.getFirstName(), service.getStatus(user, sender), sender.getImageURL());
                })
                .collect(Collectors.toList());
    }

    public void showRequests()
    {
        requestsList.setAll(getRequestsList());
        listViewRequests.setVisible(!requestsList.isEmpty());
    }

}
