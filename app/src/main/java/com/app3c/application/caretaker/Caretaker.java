package com.app3c.application.caretaker;

import java.io.Serializable;

public class Caretaker implements Serializable {
    String name;
    String email;
    String phoneNumber;
    String elderlyName;
    String elderlyPhoneNo;
    String password;

    public Caretaker(){
    }
    public Caretaker(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public Caretaker(String PhoneNo,String Name,String Email,String ElderlyName,String ElderlyPhoneNo,String Password){
        this.name = Name;
        this.email = Email;
        this.phoneNumber = PhoneNo;
        this.elderlyName = ElderlyName;
        this.elderlyPhoneNo = ElderlyPhoneNo;
        this.password = Password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getElderlyName() {
        return elderlyName;
    }

    public void setElderlyName(String elderlyName) {
        this.elderlyName = elderlyName;
    }

    public String getElderlyPhoneNo() {
        return elderlyPhoneNo;
    }

    public void setElderlyPhoneNo(String elderlyPhoneNo) {
        this.elderlyPhoneNo = elderlyPhoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
