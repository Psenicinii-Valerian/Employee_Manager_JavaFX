package it.step.controller;

import it.step.db.EmployeeManagerDB;
import it.step.model.Gender;
import it.step.model.Person;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddDialogController {
    public TextField nameTxtField;
    public DatePicker birthdatePicker;
    public RadioButton maleRadioBtn;
    public RadioButton femaleRadioBtn;
    public TextField emailTxtField;
    public TextField surnameTxtField;

    private EmployeeManagerDB db = new EmployeeManagerDB();
    private Gender gender;

    public void onAdd(ActionEvent event) {

        int id = db.getPersonID()+1;
        String name = nameTxtField.getText();
        String surname = surnameTxtField.getText();
        String email = emailTxtField.getText();
        LocalDate birthdate = birthdatePicker.getValue();

        if (!name.isEmpty() && !surname.isEmpty() && (maleRadioBtn.isSelected() || femaleRadioBtn.isSelected()) && !email.isEmpty() && birthdate != null) {
            // save the data db
            db.create(new Person(id, name, surname, gender, email, birthdate));
            // close
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        }
    }

    public void onMaleSelect(ActionEvent event) {
        this.femaleRadioBtn.setSelected(false);
        gender = Gender.MALE;
    }

    public void onFemaleSelect(ActionEvent event) {
        this.maleRadioBtn.setSelected(false);
        gender = Gender.FEMALE;
    }

}
