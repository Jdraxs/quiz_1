package com.eam.quiz.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "phoneNumber")
    private int phoneNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "Email")
    private String email;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.PERSIST)
    private List<Subject> Subject;

    public Teacher() {
        super();
    }

    public Teacher(int phoneNumber, String name, String email, List<com.eam.quiz.model.Subject> subject) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
        Subject = subject;
    }

    public Teacher(long id, int phoneNumber, String name, String email, List<com.eam.quiz.model.Subject> subject) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
        Subject = subject;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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

    public int getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
