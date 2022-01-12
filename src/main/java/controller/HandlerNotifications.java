package controller;

import domain.Event;
import javafx.stage.Stage;
import service.Service;
import window.NotificationWindow;

import java.util.List;
import java.util.stream.Collectors;

public class HandlerNotifications extends Thread{
    private List<Event> events;
    private int rootXValue;
    private int rootYValue;
    private Thread notificationThread;

    public HandlerNotifications(List<Event> events, int rootXValue, int rootYValue, Thread notificationThread) {
        this.events = events;
        this.rootXValue = rootXValue;
        this.rootYValue = rootYValue;
        this.notificationThread = notificationThread;
    }

    private List<NotificationWindow> listEventWindows()
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

    @Override
    public void run() {
        listEventWindows().forEach(x -> {
            try {
                notificationThread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Stage newNotification = new Stage();
            System.out.println(x.getEvent());
            try {
                x.start(newNotification);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                notificationThread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            newNotification.close();
        });


    }
}
