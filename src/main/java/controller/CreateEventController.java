package controller;

import domain.User;
import exception.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import service.Service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static utils.Constants.*;

public class CreateEventController {
    private User user;
    private Service service;

    @FXML
    Label labelAddImage;

    @FXML
    ImageView imageEvent;

    @FXML
    TextField textFieldName;

    @FXML
    DatePicker datePicker;

    @FXML
    TextField textFieldHour;

    @FXML
    TextArea textAreaDescription;

    @FXML
    Label succesCreate;

    @FXML
    public void initialize()
    {
        succesCreate.setVisible(false);
        textAreaDescription.setWrapText(true);
        imageEvent.setImage(new Image("C:\\ANDA\\Anul 2\\MAP\\Lab\\Teme\\SocialNetwork\\src\\main\\resources\\imgs\\EventDefault.png"));
    }

    public void handleAddImage()
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(labelAddImage.getScene().getWindow());

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageEvent.setImage(image);
        }
    }

    public void resetTextFields()
    {
        textFieldName.setText("");
        textFieldHour.setText("");
        textAreaDescription.setText("");
        datePicker.getEditor().clear();
        imageEvent.setImage(null);
    }

    public void handleSave() {
        String Name = textFieldName.getText();
        LocalDate date = datePicker.getValue();
        String hour = textFieldHour.getText();
        List<String> hourStringList = List.of(hour.split(":"));
        List<Integer> hourIntegerList = new ArrayList<>();
        hourIntegerList = hourStringList.stream().map(Integer::parseInt)
                .collect(Collectors.toList());
        LocalDateTime dateHour = date.atTime(hourIntegerList.get(0), hourIntegerList.get(1));
        String description = textAreaDescription.getText();
        String imageURL = imageEvent.getImage().getUrl();
        List<Integer> participants = new ArrayList<>();
        service.addEvent(user.getId(), Name, description, dateHour, participants, imageURL);
        succesCreate.setVisible(true);
        resetTextFields();
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setService(Service service)
    {
        this.service = service;
    }
}
