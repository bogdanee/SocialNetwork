package controller;

import domain.Event;
import javafx.application.Platform;
import javafx.stage.Stage;
import window.NotificationWindow;

import java.util.List;
import java.util.stream.Collectors;

public class HandlerNotifications {
    private List<Event> events;
    private int rootXValue;
    private int rootYValue;
    private Thread thread;

    public HandlerNotifications(List<Event> events, int rootXValue, int rootYValue) {
        this.events = events;
        this.rootXValue = rootXValue;
        this.rootYValue = rootYValue;
    }

    private List<NotificationWindow> listNotificationWindows()
    {
        return events.stream()
                .map(x -> {
                    NotificationWindow notificationWindow = new NotificationWindow();
                    notificationWindow.setEvent(x);
                    notificationWindow.setRootXValue(rootXValue);
                    notificationWindow.setRootYValue(rootYValue);
                    return notificationWindow;
                }).collect(Collectors.toList());
    }


    public void startThread()
    {
        thread = new Thread(() -> listNotificationWindows().forEach(x -> {
            Platform.runLater(() -> {
                Stage newStage = new Stage();
                try {
                    x.start(newStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }));
        thread.start();
    }

}
