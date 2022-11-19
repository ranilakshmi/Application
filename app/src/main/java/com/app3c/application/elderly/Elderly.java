package com.app3c.application.elderly;

import java.io.Serializable;


public class Elderly implements Serializable {
    String name;
    String phoneNumber;
    public Elderly(){
    }
    public Elderly(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public Elderly(String name,String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName(){return this.name;}
    public String getPhoneNo() {
        return this.phoneNumber;
    }
}
