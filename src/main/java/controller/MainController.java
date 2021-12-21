package controller;

import domain.User;
import domain.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import service.Service;

import javafx.util.Callback;
import utils.RequestStatus;
import utils.UsersStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainController {
    ObservableList<UserDTO> modelUser = FXCollections.observableArrayList();
    private Service service;
    private User user;

    @FXML
    TextField textFieldSearch;

    @FXML
    Button buttonSearch;

    @FXML
    ListView<UserDTO> listView;

    @FXML
    public void initialize()
    {
        listView.setItems(modelUser);
        setSearch();
        textFieldSearch.textProperty().addListener(o -> handleSearch());
    }

    private void setSearch()
    {
        var cellFactory = new Callback<ListView<UserDTO>, ListCell<UserDTO>>() {
            @Override
            public ListCell<UserDTO> call(ListView<UserDTO> param) {
                ListCell<UserDTO> cell = new ListCell<UserDTO>() {
                    private final AnchorPane anchorPane = new AnchorPane();
                    private final Label labelName = new Label();
                    private final Button buttonAddFriend = new Button("Add friend");
                    private final Button buttonCancelRequest = new Button ("Cancel request");
                    private final Label labelAlreadyFriends = new Label("Friends!");

                    private final HBox buttons = new HBox();
                    private final Button buttonAccept = new Button("Accept");
                    private final Button buttonDeny = new Button("Deny");

                    {
                        this.setStyle("-fx-background-color: #1a1a1a; -fx-font-size:13;");
                        labelName.setStyle("-fx-text-fill: white; ");
                        buttonAddFriend.setStyle("-fx-background-color:#248CF0FF; -fx-text-fill: white;");
                        buttonCancelRequest.setStyle("-fx-background-color:#248CF0FF; -fx-text-fill: white;");
                        buttonAccept.setStyle("-fx-background-color:#248CF0FF; -fx-text-fill: white;");
                        buttonDeny.setStyle("-fx-background-color: white; -fx-text-fill: black;");
                        labelAlreadyFriends.setStyle("-fx-text-fill: white; ");

                        anchorPane.getChildren().add(labelName);

                        AnchorPane.setRightAnchor(buttonAddFriend, 1d);
                        anchorPane.getChildren().add(buttonAddFriend);

                        AnchorPane.setRightAnchor(buttonCancelRequest, 1d);
                        anchorPane.getChildren().add(buttonCancelRequest);

                        AnchorPane.setRightAnchor(labelAlreadyFriends, 1d);

                        AnchorPane.setRightAnchor(buttons, 1d);
                        anchorPane.getChildren().add(buttons);
                        buttons.getChildren().add(buttonAccept);
                        buttons.getChildren().add(buttonDeny);
                    }

                    @Override
                    public void updateItem(UserDTO item, boolean empty) {

                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            System.out.println(user.getId() + " " + item.getId() + " " + item.getStatus());
                            labelName.setText(item.getName());
                            if (Objects.equals(item.getStatus(), UsersStatus.FRIENDS.toString()))
                            {
                                buttonAddFriend.setVisible(false);
                                buttonCancelRequest.setVisible(false);
                                labelAlreadyFriends.setVisible(true);
                            }
                            else if (Objects.equals(item.getStatus(), UsersStatus.REQUESTSENT.toString()))
                            {
                                buttonAddFriend.setVisible(false);
                                buttonCancelRequest.setVisible(true);
                                labelAlreadyFriends.setVisible(false);
                                buttons.setVisible(false);
                            }
                            else if (Objects.equals(item.getStatus(), UsersStatus.REQUESTRECEIVED.toString()))
                            {
                                buttonAddFriend.setVisible(false);
                                buttonCancelRequest.setVisible(false);
                                labelAlreadyFriends.setVisible(false);
                                buttons.setVisible(true);
                            }
                            else if (Objects.equals(item.getStatus(), UsersStatus.REJECTED.toString()) || Objects.equals(item.getStatus(), UsersStatus.NOTFRIENDS.toString()))
                            {
                                buttonAddFriend.setVisible(true);
                                buttonCancelRequest.setVisible(false);
                                labelAlreadyFriends.setVisible(false);
                                buttons.setVisible(false);
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
                                buttons.getChildren().add(labelAlreadyFriends);
                                labelAlreadyFriends.setVisible(true);
                                buttonAccept.setVisible(false);
                                buttonDeny.setVisible(false);
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
                .map(u -> new UserDTO(u.getId(), u.getLastName() + " " + u.getFirstName(), service.getStatus(user, u)))
                .collect(Collectors.toList());
    }

    public void handleSearch()
    {
        var usersList = getUserDTOList().stream()
                .filter(u -> u.getName().toLowerCase(Locale.ROOT).contains(textFieldSearch.getText().toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
        modelUser.setAll(usersList);
        listView.setVisible(!usersList.isEmpty());

    }

    public void setService(Service service)
    {
        this.service = service;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
