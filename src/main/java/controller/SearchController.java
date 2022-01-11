package controller;

import domain.User;
import domain.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.Service;
import utils.RequestStatus;
import utils.UsersStatus;
import window.ConversationWindow;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class SearchController extends MainController {
    ObservableList<UserDTO> usersList = FXCollections.observableArrayList();

    @FXML
    TextField textFieldSearch;

    @FXML
    ListView<UserDTO> listView;



    @FXML
    public void initialize() throws IOException {
        listView.setItems(usersList);
        setSearch();
        super.setDrawer();
    }

    private void setSearch()
    {
        var cellFactory = new Callback<ListView<UserDTO>, ListCell<UserDTO>>() {
            @Override
            public ListCell<UserDTO> call(ListView<UserDTO> param) {
                ListCell<UserDTO> cell = new ListCell<UserDTO>() {
                    private final AnchorPane anchorPane = new AnchorPane();
                    private final ImageView image = new ImageView();
                    private final Label labelName = new Label();
                    private final Button buttonAddFriend = new Button("Add friend");
                    private final Button buttonCancelRequest = new Button ("Cancel request");
                    private final Button buttonFriends = new Button("Friends!");

                    private final HBox buttonsAcceptDeny = new HBox();
                    private final Button buttonAccept = new Button("Accept");
                    private final Button buttonDeny = new Button("Deny");


                    {
                        this.setStyle("-fx-background-color: #070707; -fx-font-size:13;");
                        labelName.setStyle("-fx-text-fill: white; ");
                        buttonAddFriend.setStyle("-fx-background-color:#248CF0FF; -fx-text-fill: white;");
                        buttonCancelRequest.setStyle("-fx-background-color:gray; -fx-text-fill: white;");
                        buttonAccept.setStyle("-fx-background-color:#248CF0FF; -fx-text-fill: white;");
                        buttonDeny.setStyle("-fx-background-color: white; -fx-text-fill: black;");
                        buttonFriends.setStyle("-fx-background-color: #1a1a1a;   -fx-text-fill: white;");

                        image.setFitWidth(35d);
                        image.setFitHeight(35d);

                        AnchorPane.setTopAnchor(image, 3d);
                        anchorPane.getChildren().add(image);

                        AnchorPane.setLeftAnchor(labelName, 40d);
                        anchorPane.getChildren().add(labelName);

                        AnchorPane.setRightAnchor(buttonAddFriend, 1d);
                        AnchorPane.setTopAnchor(buttonAddFriend, 5d);
                        anchorPane.getChildren().add(buttonAddFriend);

                        AnchorPane.setRightAnchor(buttonCancelRequest, 1d);
                        AnchorPane.setTopAnchor(buttonCancelRequest, 5d);
                        anchorPane.getChildren().add(buttonCancelRequest);


                        AnchorPane.setRightAnchor(buttonFriends, 1d);
                        AnchorPane.setTopAnchor(buttonFriends, 5d);
                        anchorPane.getChildren().add(buttonFriends);


                        AnchorPane.setRightAnchor(buttonsAcceptDeny, 1d);
                        anchorPane.getChildren().add(buttonsAcceptDeny);
                        buttonsAcceptDeny.getChildren().add(buttonAccept);
                        buttonsAcceptDeny.getChildren().add(buttonDeny);
                    }

                    @Override
                    public void updateItem(UserDTO item, boolean empty) {

                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            labelName.setText(item.getName());
                            image.setImage(new Image(item.getImageURL()));

                            if (Objects.equals(item.getStatus(), UsersStatus.FRIENDS.toString()))
                            {
                                buttonAddFriend.setVisible(false);
                                buttonCancelRequest.setVisible(false);
                                buttonFriends.setVisible(true);
                                buttonsAcceptDeny.setVisible(false);
                            }
                            else if (Objects.equals(item.getStatus(), UsersStatus.REQUESTSENT.toString()))
                            {
                                buttonAddFriend.setVisible(false);
                                buttonCancelRequest.setVisible(true);
                                buttonFriends.setVisible(false);
                                buttonsAcceptDeny.setVisible(false);

                            }
                            else if (Objects.equals(item.getStatus(), UsersStatus.REQUESTRECEIVED.toString()))
                            {
                                buttonAddFriend.setVisible(false);
                                buttonCancelRequest.setVisible(false);
                                buttonFriends.setVisible(false);
                                buttonsAcceptDeny.setVisible(true);
                            }
                            else if (Objects.equals(item.getStatus(), UsersStatus.REJECTED.toString()) || Objects.equals(item.getStatus(), UsersStatus.NOTFRIENDS.toString()))
                            {
                                buttonAddFriend.setVisible(true);
                                buttonCancelRequest.setVisible(false);
                                buttonFriends.setVisible(false);
                                buttonsAcceptDeny.setVisible(false);
                            }

                            buttonAddFriend.setOnAction((ActionEvent event) -> {
                                service.sendFriendRequest(user.getId(), item.getId(), LocalDateTime.now());
                                buttonAddFriend.setVisible(false);
                                buttonCancelRequest.setVisible(true);
                            });
                            buttonCancelRequest.setOnAction((ActionEvent event) -> {
                                service.deleteRequest(user.getId(), item.getId());
                                buttonAddFriend.setVisible(true);
                                buttonCancelRequest.setVisible(false);
                            });
                            buttonAccept.setOnAction((ActionEvent event) -> {
                                service.updateRequest(item.getId(), user.getId(), LocalDateTime.now(), RequestStatus.APPROVED.toString());
                                item.setStatus(UsersStatus.FRIENDS.toString());
                                buttonsAcceptDeny.setVisible(false);
                                buttonFriends.setVisible(true);
                            });
                            buttonDeny.setOnAction((ActionEvent event) -> {
                                service.deleteRequest(item.getId(), user.getId());
                                item.setStatus(UsersStatus.REJECTED.toString());
                                buttonsAcceptDeny.setVisible(false);
                                buttonAddFriend.setVisible(true);
                            });
                            setGraphic(anchorPane);
                        }
                    }
                };
                return cell;
            }
        };
        listView.setCellFactory(cellFactory);
    }

    private List<UserDTO> getUserDTOList()
    {
        return service.getAllUsers().stream()
                .filter(u -> u.getId() != user.getId())
                .map(u -> new UserDTO(u.getId(), u.getLastName() + " " + u.getFirstName(), service.getStatus(user, u), u.getImageURL()))
                .collect(Collectors.toList());
    }

    public void handleSearch()
    {
        var usersList = getUserDTOList().stream()
                .filter(u -> u.getName().toLowerCase(Locale.ROOT).contains(textFieldSearch.getText().toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
        this.usersList.setAll(usersList);
        listView.setVisible(!usersList.isEmpty());

    }

    private void showConversation(User sender, User receiver) throws Exception {
        Stage newWindow = new Stage();
        ConversationWindow conversationWindow = new ConversationWindow();
        conversationWindow.setSender(sender);
        conversationWindow.setReceiver(receiver);
        conversationWindow.setService(service);
        conversationWindow.start(newWindow);
    }
}
