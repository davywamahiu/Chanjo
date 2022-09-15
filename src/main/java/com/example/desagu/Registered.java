package com.example.desagu;

public class Registered {

    private String fullName,gender,birthCert,vaccine,status,date,doctor;

    public Registered(String fullName, String gender, String birthCert) {
        this.fullName = fullName;
        this.gender = gender;
        this.birthCert = birthCert;
    }

    public Registered( String vaccine, String status, String date, String doctor) {
        this.vaccine = vaccine;
        this.status = status;
        this.date = date;
        this.doctor = doctor;
    }



    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthCert() {
        return birthCert;
    }

    public String getVaccine() {
        return vaccine;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getDoctor() {
        return doctor;
    }
}
