package it.step.controller;

import it.step.db.LoginManagerDB;
import it.step.model.Login;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class RegisterDialogController {

    public TextField regUsernameTxtField;
    public PasswordField regPasswordTxtField;
    public TextField firstNameTxtField;
    public TextField lastNameTxtField;

    private LoginManagerDB db = new LoginManagerDB();

    public void onSignUp(ActionEvent event) {

        String firstName = firstNameTxtField.getText();
        String lastName = lastNameTxtField.getText();
        String username = regUsernameTxtField.getText();
        String password = regPasswordTxtField.getText();

        if (!firstName.isEmpty() && !lastName.isEmpty() && username.length()>=8 && password.length()>=8 ) {

            /*
             * 1. De adaugat FXML pentru o fereastra noua - register.fxml
             * 2. De afisat campurile necesare pentru clasa User
             * 3. Cand se da click pe register - de salvat in baza de date utilizatorul nou
             * 4. Inchidem dialogul de inregistrare si afisam login-ul
             */

            // save the data db
            db.create(new Login(firstName, lastName, username, password, true));
            // close
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

        } else {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please fulfill all the field and make sure your username and password is at least 8 characters length!");
            alert.setTitle("Incomplete Sign Up!");
            alert.show();
        }
        
    }
}
