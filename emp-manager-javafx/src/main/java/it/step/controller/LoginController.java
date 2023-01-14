package it.step.controller;

import it.step.db.LoginManagerDB;
import it.step.model.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    public TextField usernameTxtField;
    public TextField passwordTxtField;
    private LoginManagerDB db = new LoginManagerDB();

    public Login getLogin() {
        return new Login(usernameTxtField.getText(), passwordTxtField.getText());
    }

    public void onLogin(ActionEvent event) throws IOException {
        /*
            * 0. Adaugam ID pentru campurile username si password
            * 1. Citim datele introduse in text field-uri
            * 2. Validam datele - daca sunt complete
            * 3. Daca datele nu sunt complete - afisam o alerta (#see main scene controller - delete)
            * 4. Cream o clasa noua: User (campuri: firstName/lastName/username/password/boolean isActive)
            * 5. SELECT user din baza de date unde username = usernameTxtField.getText() si parola = passwordTxtField.getText()
            * 6. Daca utilizatorul a fost gasit - deschidem fereastra principala
            * 6.2 Daca utilizatorul nu a fost gasit afisam un mesaj de eroare
         */

        String username = usernameTxtField.getText();
        String password = passwordTxtField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {

            // save the data db
            if (db.verify(new Login(username, password)) == 1){

                                            // !!! 100% workable !!!
//                AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/manager.fxml"));
//                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//                Scene scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();

                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/manager.fxml"));
                AnchorPane parent = (AnchorPane)loader.load();
                MainSceneController controller = (MainSceneController)loader.getController();
                controller.getLogin(new Login(username, password));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

            } else {
                Alert alert;
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please make sure you've entered the correct username and password and then try again!");
                alert.setTitle("Incomplete Sign Up!");
                alert.show();
            }

        }

    }

    public void onRegister(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/register.fxml"));
        AnchorPane parent = (AnchorPane)loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Sign Up window");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }



}
