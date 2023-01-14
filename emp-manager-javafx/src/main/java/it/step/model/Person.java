package it.step.model;

import java.time.LocalDate;

public class Person {

    private int id;
    private String name;
    private String surname;

    private Gender gender;

    private String email;
    private LocalDate birthdate;

    public Person(int id, String name, String surname, Gender gender, String email, LocalDate birthdate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.email = email;
        this.birthdate = birthdate;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }


    public String getGender() {
        return this.gender.toString().equalsIgnoreCase("MALE") ?  "Male" : "Female";
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender=" + gender +  '\'' +
                ", email='" + email + '\'' +
                ", Birthdate=" + birthdate +
                '}';
    }
}
