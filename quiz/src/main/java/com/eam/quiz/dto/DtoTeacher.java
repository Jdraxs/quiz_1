package com.eam.quiz.dto;

import com.eam.quiz.model.Subject;

import java.util.List;

public class DtoTeacher {
    private int phoneNumber;
    private String name;
    private String email;
    private List<Subject> Subject;

    public DtoTeacher() {
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public List<Subject> getSubject() {
        return Subject;
    }
    public void setSubject(List<Subject> subject) {
        Subject = subject;
    }
}