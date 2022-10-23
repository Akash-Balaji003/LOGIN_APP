package com.example.dsa;

public class User {
    public String email, name, phone, DOB, course, semester;

    public User(){

    }

    public User(String name, String course, String semester, String DOB, String phone, String email){
        this.name = name;
        this.course = course;
        this.semester = semester;
        this.DOB = DOB;
        this.phone = phone;
        this.email = email;
    }
}
