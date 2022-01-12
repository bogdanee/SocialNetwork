package controller;

import domain.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;


import java.util.List;

public class NotificationController {
    private Event event;

    @FXML
    Label labelEvent;

    @FXML
    public void initialize(){}

    public void handleOK()
    {
        Stage currentStage = (Stage) labelEvent.getScene().getWindow();
        currentStage.close();
    }

    public void setEvent(Event event) {
        this.event = event;
        labelEvent.setText("Event " + event.getName() + "\n"
                            + "Date: " + event.getDate());
    }

}
