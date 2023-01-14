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

public class EditDialogController {

    public RadioButton maleRadioBtn;
    public RadioButton femaleRadioBtn;
    private Person copy;
    public TextField nameTxtField;
    public TextField surnameTxtField;
    public TextField emailTxtField;
    public DatePicker birthdatePicker;

    private EmployeeManagerDB db = new EmployeeManagerDB();

    public void setPerson(Person person) {
        copy = person;
        // read name
        nameTxtField.setText(person.getName());
        //read surname
        surnameTxtField.setText(person.getSurname());
        // read gender
        if (person.getGender().equalsIgnoreCase("MALE")) {
            maleRadioBtn.setSelected(true);
        } else {
            femaleRadioBtn.setSelected(true);
        }
        // read email
        emailTxtField.setText(person.getEmail());
        // read birthdate
        birthdatePicker.setValue(person.getBirthdate());
    }

    public Person getResult() {
        return new Person(copy.getId(), nameTxtField.getText(), surnameTxtField.getText(),
                          maleRadioBtn.isSelected() ? Gender.MALE : Gender.FEMALE,
                          emailTxtField.getText(), birthdatePicker.getValue());
    }

    public void onEdit(ActionEvent event) {

        int id = copy.getId();

        if (nameTxtField.getText().isEmpty()) {
            nameTxtField.setText(copy.getName());
        }

        if (surnameTxtField.getText().isEmpty()) {
            surnameTxtField.setText(copy.getSurname());
        }

        if (emailTxtField.getText().isEmpty()) {
            emailTxtField.setText(copy.getEmail());
        }

        LocalDate birthdate = birthdatePicker.getValue();
        if (birthdate == null) {
            birthdatePicker.setValue(copy.getBirthdate());
        }

        String name = nameTxtField.getText();
        String surname = surnameTxtField.getText();
        String email = emailTxtField.getText();

        // edit the data db
        db.update(new Person(id, name, surname, maleRadioBtn.isSelected() ? Gender.MALE : Gender.FEMALE, email, birthdate));

        // close
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void onMaleSelect(ActionEvent event) {
        this.femaleRadioBtn.setSelected(false);
    }

    public void onFemaleSelect(ActionEvent event) {
        this.maleRadioBtn.setSelected(false);
    }
}
