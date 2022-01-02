package controller;

import exception.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static utils.Constants.ERROR_CODE_INVALID_FN;
import static utils.Constants.ERROR_CODE_INVALID_LN;

public class ProfileController extends MainController{

    @FXML
    ImageView imageUser;

    @FXML
    Label labelChangeImage;

    @FXML
    TextField textFieldLastName;

    @FXML
    TextField textFieldFirstName;

    @FXML
    Button buttonSave;

    @FXML
    Label labelErrorLastName;

    @FXML
    Label labelErrorFirstName;

    @FXML
    Label labelErrorImage;

    @FXML
    Label labelSave;

    @FXML
    public void initialize() throws IOException {
        super.setDrawer();
        resetLabels();
    }

    private void resetLabels()
    {
        labelSave.setText("");
        labelErrorLastName.setText("");
        labelErrorFirstName.setText("");
        labelErrorImage.setText("");
    }

    public void handleSave()
    {
        resetLabels();
        String newLastName = textFieldLastName.getText();
        String newFirstName = textFieldFirstName.getText();
        String newImageURL = imageUser.getImage().getUrl();
        if(imageUser.getImage().errorProperty().getValue())
        {
            labelErrorImage.setText("Invalid photo uploaded!");
        }
        else if (!Objects.equals(newLastName, user.getLastName()) ||
                !Objects.equals(newFirstName, user.getFirstName()) ||
                !Objects.equals(newImageURL, user.getImageURL()))
        {

            try{
                service.updateUser(newFirstName, newLastName, user.getUsername(), user.getPassword(), newImageURL);
                labelSave.setText("Changes were successfully saved!");
                user.setFirstName(newFirstName);
                user.setLastName(newLastName);
                user.setImageURL(newImageURL);
            }
            catch (ValidationException ve)
            {
                String error = ve.getMessage();
                if (error.contains(ERROR_CODE_INVALID_FN))
                    labelErrorFirstName.setText("First name must only contain letters");
                if (error.contains(ERROR_CODE_INVALID_LN))
                    labelErrorLastName.setText("Last name must only contain letters");
            }
        }

    }

    public void handleChangeImage()
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(labelChangeImage.getScene().getWindow());

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageUser.setImage(image);
        }

    }

    public ImageView getImageUser() {
        return imageUser;
    }

    public TextField getTextFieldLastName() {
        return textFieldLastName;
    }

    public TextField getTextFieldFirstName() {
        return textFieldFirstName;
    }

}
