package controller;

import domain.ReplyMessage;
import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConversationController extends MainController{
    ObservableList<ReplyMessage> messagesList = FXCollections.observableArrayList();

    private User sender;
    private User receiver;
    private int idMessageReply;

    @FXML
    ListView listViewMessages;

    @FXML
    ImageView imageReceiver;

    @FXML
    Label labelReceiver;

    @FXML
    TextField textFieldMessage;

    @FXML
    HBox hBoxReply;

    @FXML
    Label labelReply;

    @FXML
    public void initialize() throws IOException {
        listViewMessages.setItems(messagesList);
        setMessages();
        super.setDrawer();
        hBoxReply.setVisible(false);
        idMessageReply = 0;
    }
    
    private void setMessages()
    {
        var cellFactory = new Callback<ListView<ReplyMessage>, ListCell<ReplyMessage>>() {
            @Override
            public ListCell<ReplyMessage> call(ListView<ReplyMessage> param) {
                ListCell<ReplyMessage> cell = new ListCell<ReplyMessage>() {
                    private final AnchorPane anchorPane = new AnchorPane();
                    private final Label labelMessageSender = new Label();
                    private final Label labelMessageReceiver = new Label();
                    private final Button labelMessageSenderReplied = new Button();
                    private final Button labelMessageReceiverReplied = new Button();
                    private final VBox vBoxSender = new VBox();
                    private final VBox vBoxReceiver = new VBox();

                    {
                        this.setStyle("-fx-background-color: #000000; -fx-font-size:15;");
                        AnchorPane.setRightAnchor(vBoxSender, 0d);
                        AnchorPane.setLeftAnchor(vBoxReceiver, 0d);

                        vBoxSender.getChildren().addAll(labelMessageSenderReplied, labelMessageSender);
                        vBoxReceiver.getChildren().addAll(labelMessageReceiverReplied, labelMessageReceiver);
                        anchorPane.getChildren().addAll(vBoxSender, vBoxReceiver);
                        labelMessageSender.setStyle("-fx-background-color: grey; -fx-text-fill: white; -fx-padding:5; -fx-background-radius: 5;");
                        labelMessageReceiver.setStyle("-fx-background-color: #248cf0; -fx-text-fill: white; -fx-padding:5; -fx-background-radius: 5;");
                        labelMessageReceiverReplied.setStyle("-fx-background-color: #1a1a1a; -fx-text-fill: white; -fx-padding:5; -fx-background-radius: 5; -fx-font-size:10;");
                        labelMessageSenderReplied.setStyle("-fx-background-color: #1a1a1a; -fx-text-fill: white; -fx-padding:5; -fx-background-radius: 5; -fx-font-size:10;");

                        vBoxSender.setAlignment(Pos.CENTER_RIGHT);
                        vBoxReceiver.setAlignment(Pos.CENTER_LEFT);
                    }

                    @Override
                    public void updateItem(ReplyMessage item, boolean empty) {

                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Boolean isReply = false;
                            if (item.getMessageToReply() != null) {
                                isReply = true;
                            }
                            if (item.getFrom() == sender.getId())
                            {
                                if (!isReply)
                                {
                                    labelMessageSenderReplied.setVisible(false);
                                }
                                else{
                                    labelMessageSenderReplied.setText(item.getMessageToReply().getMessage());
                                    labelMessageSenderReplied.setVisible(true);
                                }
                                labelMessageSender.setText(item.getMessage());
                                vBoxSender.setVisible(true);
                                vBoxReceiver.setVisible(false);
                            }
                            else
                            {
                                if (!isReply)
                                {
                                    labelMessageReceiverReplied.setVisible(false);
                                }
                                else
                                {
                                    labelMessageReceiverReplied.setText(item.getMessageToReply().getMessage());
                                    labelMessageReceiverReplied.setVisible(true);
                                }
                                labelMessageReceiver.setText(item.getMessage());
                                vBoxReceiver.setVisible(true);
                                vBoxSender.setVisible(false);
                            }
                            labelMessageSender.setOnMouseClicked(event -> {
                                idMessageReply = (int) item.getId();
                                hBoxReply.setVisible(true);
                                labelReply.setText(item.getMessage());
                            });
                            labelMessageReceiver.setOnMouseClicked(event -> {
                                idMessageReply = (int) item.getId();
                                hBoxReply.setVisible(true);
                                labelReply.setText(item.getMessage());
                            });

                            setGraphic(anchorPane);
                        }
                    }
                };
                return cell;
            }
        };
        listViewMessages.setCellFactory(cellFactory);
    }

    public void handleMessage()
    {
        String message = textFieldMessage.getText();
        List<Integer> receivers = new ArrayList<>();
        receivers.add(receiver.getId());
        if (!message.isEmpty())
            service.addMessage(sender.getId(), receivers, message, LocalDateTime.now(), idMessageReply);

        showMessages();
        textFieldMessage.setText("");
        hBoxReply.setVisible(false);
        idMessageReply = 0;
    }

    public void handleCancelReply()
    {
        hBoxReply.setVisible(false);
        idMessageReply = 0;
    }

    private List<ReplyMessage> getMessagesList()
    {
        var messages = service.getAllMessageFrom2Users(sender.getId(), receiver.getId());
        //messages.forEach(System.out::println);
        return messages;
    }

    public void showMessages() {
        messagesList.setAll(getMessagesList());
        //listViewMessages.scrollTo(listViewMessages.getItems().size() - 1);

    }


    public void setReceiver(User receiver) {
        this.receiver = receiver;
        this.sender = super.user;
        this.setImageAndName();
    }

    private void setImageAndName()
    {
        imageReceiver.setImage(new Image(receiver.getImageURL()));
        labelReceiver.setText(receiver.getLastName() + " " + receiver.getFirstName());

    }




}
