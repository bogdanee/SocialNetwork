package controller;

import domain.Event;
import domain.User;
import domain.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.Constants;
import utils.RequestStatus;
import utils.UsersStatus;
import window.CreateEventWindow;
import window.RemoveFriendWindow;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class EventController extends MainController{
    ObservableList<Event> eventsList = FXCollections.observableArrayList();
    ObservableList<Event> eventsSearchList = FXCollections.observableArrayList();

    @FXML
    ListView<Event> listViewEvents;

    @FXML
    ImageView imageEvent;

    @FXML
    Label labelEventName;

    @FXML
    Label labelOrganiser;

    @FXML
    Label labelDateEvent;

    @FXML
    TextArea textAreaDescription;

    @FXML
    Button buttonSubscribe;

    @FXML
    Button buttonUnSubscribe;

    @FXML
    ListView<Event> listViewSearch;

    @FXML
    TextField textFieldSearch;

    @FXML
    Button buttonCreate;

    @FXML
    public void initialize() throws IOException {
        listViewEvents.setItems(eventsList);
        setEvents();
        listViewSearch.setItems(eventsSearchList);
        setSearchEvents();
        this.hideDetails();
        super.setDrawer();
    }
    private void setEvents()
    {
        var cellFactory = new Callback<ListView<Event>, ListCell<Event>>() {
            @Override
            public ListCell<Event> call(ListView<Event> param) {
                ListCell<Event> cell = new ListCell<Event>() {
                    private final AnchorPane anchorPane = new AnchorPane();
                    private final VBox vbox = new VBox();
                    private final Label labelDate = new Label();
                    private final ImageView image = new ImageView();
                    private final Label labelName = new Label();

                    {
                        this.setStyle("-fx-background-color: #000000; -fx-font-size:15;");
                        labelName.setStyle("-fx-text-fill: white; ");
                        labelDate.setStyle("-fx-text-fill: white; -fx-font-size: 10");
                        vbox.setSpacing(3d);

                        image.setFitWidth(35d);
                        image.setFitHeight(35d);

                        AnchorPane.setTopAnchor(image, 3d);
                        anchorPane.getChildren().add(image);

                        vbox.getChildren().addAll(labelName, labelDate);
                        AnchorPane.setLeftAnchor(vbox, 40d);
                        anchorPane.getChildren().add(vbox);
                    }

                    @Override
                    public void updateItem(Event item, boolean empty) {

                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            labelName.setText(item.getName());
                            image.setImage(new Image(item.getImageURL()));
                            labelDate.setText(item.getDate().format(Constants.DATE_TIME_FORMATTER));
                            setGraphic(anchorPane);

                            image.setOnMouseClicked(event ->
                            {
                                imageEvent.setImage(new Image(item.getImageURL()));
                                labelEventName.setText(item.getName());
                                User organiser = service.findUser(item.getOrganiser());
                                labelOrganiser.setText("Organiser: " + organiser.getLastName() + " " + organiser.getFirstName());
                                labelDateEvent.setText("Date: " + item.getDate().format(Constants.DATE_TIME_FORMATTER));
                                textAreaDescription.setText("Description: " + item.getDescription());
                                textAreaDescription.setWrapText(true);
                                showDetails();
                                buttonSubscribe.setVisible(false);
                                listViewSearch.setVisible(false);
                            });

                            handleSubscribeButtons(item);
                        }
                    }


                };
                return cell;
            }
        };
        listViewEvents.setCellFactory(cellFactory);
    }

    private List<Event> getEventsList()
    {
        return service.getAllEventsForUser(user.getId());
    }

    public void showEvents()
    {
        eventsList.setAll(getEventsList());
        listViewEvents.setVisible(!eventsList.isEmpty());
    }

    public void hideDetails()
    {
        imageEvent.setVisible(false);
        labelEventName.setVisible(false);
        labelOrganiser.setVisible(false);
        labelDateEvent.setVisible(false);
        textAreaDescription.setVisible(false);
        buttonSubscribe.setVisible(false);
        buttonUnSubscribe.setVisible(false);
        listViewSearch.setVisible(false);
    }

    public void showDetails()
    {
        imageEvent.setVisible(true);
        labelEventName.setVisible(true);
        labelOrganiser.setVisible(true);
        labelDateEvent.setVisible(true);
        textAreaDescription.setVisible(true);
        buttonSubscribe.setVisible(true);
        buttonUnSubscribe.setVisible(true);
    }


    public void setSearchEvents()
    {
        var cellFactory = new Callback<ListView<Event>, ListCell<Event>>() {
            @Override
            public ListCell<Event> call(ListView<Event> param) {
                ListCell<Event> cell = new ListCell<Event>() {
                    private final AnchorPane anchorPane = new AnchorPane();
                    private final ImageView image = new ImageView();
                    private final Label labelName = new Label();

                    {

                        this.setStyle("-fx-background-color: #1a1a1a; -fx-font-size:13;");
                        labelName.setStyle("-fx-text-fill: white; ");

                        image.setFitWidth(35d);
                        image.setFitHeight(35d);

                        AnchorPane.setTopAnchor(image, 3d);
                        anchorPane.getChildren().add(image);

                        anchorPane.getChildren().add(labelName);
                        AnchorPane.setLeftAnchor(labelName, 40d);
                    }

                    @Override
                    public void updateItem(Event item, boolean empty) {

                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            labelName.setText(item.getName());
                            image.setImage(new Image(item.getImageURL()));

                            setGraphic(anchorPane);

                            image.setOnMouseClicked(event ->
                            {
                                imageEvent.setImage(new Image(item.getImageURL()));
                                labelEventName.setText(item.getName());
                                User organiser = service.findUser(item.getOrganiser());
                                labelOrganiser.setText("Organiser: " + organiser.getLastName() + " " + organiser.getFirstName());
                                labelDateEvent.setText("Date: " + item.getDate().format(Constants.DATE_TIME_FORMATTER));
                                textAreaDescription.setText("Description: " + item.getDescription());
                                textAreaDescription.setWrapText(true);
                                showDetails();

                                if (item.getParticipants().contains(user.getId()))
                                {
                                    buttonSubscribe.setVisible(false);
                                    buttonUnSubscribe.setVisible(true);
                                }
                                else
                                {
                                    buttonSubscribe.setVisible(true);
                                    buttonUnSubscribe.setVisible(false);
                                }

                                listViewSearch.setVisible(false);
                            });

                            handleSubscribeButtons(item);
                        }
                    }
                };
                return cell;
            }
        };
        listViewSearch.setCellFactory(cellFactory);
    }

    private void handleSubscribeButtons(Event item) {
        buttonUnSubscribe.setOnAction((ActionEvent event) ->
        {
            service.deleteParticipant(item.getId(), user.getId());
            buttonSubscribe.setVisible(true);
            buttonUnSubscribe.setVisible(false);
            showEvents();
        });

        buttonSubscribe.setOnAction((ActionEvent event) ->
        {
            service.addParticipant(item.getId(), user.getId());
            buttonSubscribe.setVisible(false);
            buttonUnSubscribe.setVisible(true);
            showEvents();
        });
    }

    public void handleSearch()
    {
        var eventsList = service.getAllEvents().stream()
                .filter(u -> u.getName().toLowerCase(Locale.ROOT).contains(textFieldSearch.getText().toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
        this.eventsSearchList.setAll(eventsList);
        listViewSearch.setVisible(!eventsSearchList.isEmpty());
    }

    public void handleCreate() throws Exception {
        Stage currentStage = (Stage) listViewEvents.getScene().getWindow();
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(currentStage);

        CreateEventWindow createEventWindow = new CreateEventWindow();
        createEventWindow.setUser(user);
        createEventWindow.setService(service);
        createEventWindow.start(newWindow);
    }

}
