package controller;

import domain.User;
import domain.UserDTO;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import service.Service;

import javafx.util.Callback;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
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
                    private final Label label = new Label();
                    private final Button button = new Button("Action");

                    {
                        this.setStyle("-fx-background-color: #1a1a1a; -fx-font-size:13;");
                        label.setStyle("-fx-text-fill: white; ");
                        button.setStyle("-fx-background-color:#248CF0FF; -fx-text-fill: white;");

                        anchorPane.getChildren().add(label);
                        AnchorPane.setRightAnchor(button, 1d);
                        anchorPane.getChildren().add(button);
                    }

                    @Override
                    public void updateItem(UserDTO item, boolean empty) {

                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            label.setText(item.getName());
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
                .map(u -> new UserDTO(u.getId(), u.getLastName() + " " + u.getFirstName(), "status"))
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
