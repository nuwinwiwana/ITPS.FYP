package com.example.adminapp;

public class usermodel {

    private String fullname;
    private String email;
    private String phone;
    private String password;
    private String ic;


    public usermodel() {}

    public usermodel(String email, String fullname,String password,String phone) {
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
        this.password = password;
        this.ic = ic;



    }

    public String getFullname() {
        return fullname;
    }

    public String getemail() {
        return email;
    }

    public String getPhone(){
        return phone;
    }

    public String getPassword(){
        return password;
    }
    public String getIc(){
        return ic;
    }
}
