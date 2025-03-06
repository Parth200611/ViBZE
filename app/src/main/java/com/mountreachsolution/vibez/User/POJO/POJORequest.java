package com.mountreachsolution.vibez.User.POJO;

public class POJORequest {
    String susername;
    String rusername;
    String name;
    String image;
    String email;

    public POJORequest(String susername, String rusername, String name, String image, String email) {
        this.susername = susername;
        this.rusername = rusername;
        this.name = name;
        this.image = image;
        this.email = email;
    }

    public String getSusername() {
        return susername;
    }

    public void setSusername(String susername) {
        this.susername = susername;
    }

    public String getRusername() {
        return rusername;
    }

    public void setRusername(String rusername) {
        this.rusername = rusername;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
