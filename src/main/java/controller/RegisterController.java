package controller;

import exception.RepositoryException;
import exception.ValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import service.Service;
import window.LoginWindow;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static utils.Constants.*;

public class RegisterController {

    private Service service;

    @FXML
    TextField textFieldLastName;

    @FXML
    TextField textFieldFirstName;

    @FXML
    TextField textFieldUsername;

    @FXML
    TextField textFieldPassword;

    @FXML
    Button buttonSignIn;

    @FXML
    Button buttonReturn;

    @FXML
    Label labelErrorLastName;

    @FXML
    Label labelErrorFirstName;

    @FXML
    Label labelErrorUsername;

    @FXML
    Label labelErrorPassword;

    private void resetLabels()
    {
        labelErrorFirstName.setText("");
        labelErrorPassword.setText("");
        labelErrorUsername.setText("");
        labelErrorLastName.setText("");
    }

    public void handleSignUp()
    {
        try
        {
            String lastName = textFieldLastName.getText().toString();
            String firstName = textFieldFirstName.getText().toString();
            String username = textFieldUsername.getText().toString();
            String password = textFieldPassword.getText().toString();
            service.addUser(firstName, lastName, username, password);
            this.handleReturn();
        }
        catch (ValidationException ve)
        {
            resetLabels();
            String error = ve.getMessage().toString();
            if (error.contains(ERROR_CODE_INVALID_FN))
                labelErrorFirstName.setText("First name must only contain letters");
            if (error.contains(ERROR_CODE_INVALID_LN))
                labelErrorLastName.setText("Last name must only contain letters");
            if (error.contains(ERROR_CODE_INVALID_UN))
                labelErrorUsername.setText("Username should have at least 8 characters");
            if (error.contains(ERROR_CODE_INVALID_P))
                labelErrorPassword.setText("Password should have at least 8 characters");
        }
        catch (RepositoryException re) {
            labelErrorUsername.setText(re.getMessage());
        }
        catch(Exception e){
                System.out.println(e.getMessage());
        }

    }

    public void handleReturn() throws Exception {
        Stage stage = (Stage) buttonSignIn.getScene().getWindow();
        LoginWindow login = new LoginWindow();
        login.start(stage);
    }

    public void setService(Service service) {
        this.service = service;
    }
}
