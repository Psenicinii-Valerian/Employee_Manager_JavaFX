package it.step.controller;

import it.step.db.EmployeeManagerDB;
import it.step.db.LoginManagerDB;
import it.step.model.Gender;
import it.step.model.Login;
import it.step.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {
    public TableColumn<Person, Integer> idCol;
    public TableColumn<Person, String> nameCol;
    public TableColumn<Person, String> surnameCol;
    public TableColumn<Person, String> genderCol;
    public TableColumn<Person, String> emailCol;
    public TableColumn<Person, LocalDate> birthdateCol;
    public TableView<Person> table;
    private EmployeeManagerDB db = new EmployeeManagerDB();
    private LoginManagerDB ldb = new LoginManagerDB();

    private Login copyLogin;

    public MainSceneController() {
    }

    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Person> list = FXCollections.observableArrayList();
        List<Person> persons = this.db.selectAll();

        for(Person p: persons) {
            list.add(p);
        }

        // Link person list with table
        table.setItems(list);
        // Link id with idCol
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        // Link name with nameCol
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        // Link surname with nameCol
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        // Link gender with nameCol
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        // Link email with nameCol
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        // Link birthdate with birthdateCol
        birthdateCol.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }

    public void onAdd(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/add.fxml"));
        AnchorPane parent = (AnchorPane)loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Add employee window");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        AddDialogController dialogController = (AddDialogController)loader.getController();
        int id = db.getPersonID();
        String name = dialogController.nameTxtField.getText();
        String surname = dialogController.surnameTxtField.getText();
        Gender gender = dialogController.maleRadioBtn.isSelected() ? Gender.MALE : Gender.FEMALE;
        String email = dialogController.emailTxtField.getText();
        LocalDate birthdate = (LocalDate)dialogController.birthdatePicker.getValue();
        if (!name.isEmpty() && !surname.isEmpty() && !gender.toString().isEmpty() && !email.isEmpty() && !birthdate.toString().isEmpty()) {
            this.table.getItems().add(new Person(id, name, surname, gender, email, birthdate));
        }
    }

    public void onEdit(ActionEvent event) throws IOException {
        int idx = this.table.getSelectionModel().getSelectedIndex();
        if (idx != -1) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/edit.fxml"));
            AnchorPane parent = (AnchorPane)loader.load();
            EditDialogController controller = (EditDialogController)loader.getController();
            controller.setPerson((Person)this.table.getItems().get(idx));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Edit employee window");
            stage.setScene(scene);
            stage.showAndWait();
            controller.nameTxtField.getText();
            controller.surnameTxtField.getText();
            controller.femaleRadioBtn.setSelected(true);
            controller.emailTxtField.getText();
            controller.birthdatePicker.getValue();
            Person person = controller.getResult();
            this.table.getItems().set(idx, person);
            this.table.refresh();
        }

    }

    public void onDelete(ActionEvent event) {
        int idx = this.table.getSelectionModel().getSelectedIndex();
        Alert alert;
        if (idx != -1) {

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(" Attention! Please confirm!");
            alert.setContentText("Are you sure you want to delete? \n" + ((Person)this.table.getItems().get(idx)).toString());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                // delete the data db
                db.delete(this.table.getItems().get(idx).getId());

                // delete the data table
                this.table.getItems().remove(idx);
            }
        } else {
            alert = new Alert(AlertType.WARNING);
            alert.setContentText("Please select something");
            alert.setTitle("Title dialog");
            alert.show();
        }

    }

    public void getLogin(Login login) {
        this.copyLogin = login;
    }

    public void onClose(ActionEvent event) {

        ldb.updateStatus(copyLogin);

        System.out.println("LEFT APPLICATION");

        System.exit(0);
    }

}
