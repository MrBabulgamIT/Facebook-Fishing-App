package com.babulgam.facebookdemologinsysytem;

public class UserRegistration {

    private String First_Name,Surname,Email_Number,Password, Gender,Date;

    public UserRegistration(String first_Name, String surname, String email_Number,
                            String password, String gender, String date) {
        First_Name = first_Name;
        Surname = surname;
        Email_Number = email_Number;
        Password = password;
        Gender = gender;
        Date = date;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail_Number() {
        return Email_Number;
    }

    public void setEmail_Number(String email_Number) {
        Email_Number = email_Number;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
