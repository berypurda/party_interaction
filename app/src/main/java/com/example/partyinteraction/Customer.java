package com.example.partyinteraction;

public class Customer {
    private String name;
    private String email;
    private String info;
    private String gender;
    private int imageresource;

    public Customer() {}

    public Customer(String name, String email, String info, String gender, int imageresource) {
        this.name = name;
        this.email = email;
        this.info = info;
        this.gender = gender;
        this.imageresource = imageresource;
    }

    public String getEmail() {return email;}

    public int getImageresource() {
        return imageresource;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getGender() {
        return gender;
    }

}
