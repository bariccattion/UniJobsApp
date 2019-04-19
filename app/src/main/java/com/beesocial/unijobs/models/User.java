package com.beesocial.unijobs.models;

import java.io.Serializable;

@SuppressWarnings("serial")

public class User implements Serializable{

    private String ps, id, email, name, image;

    public User(String id, String email, String name, String image, String ps) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.image = image;
        this.ps = ps;
    }

    public User(UserLogin user) {
        this.email = user.getEmail();
        this.name = "";
    }

    public String getId() {
        return id;
    }

    public String getPs() {
        return ps;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }



}
