package com.eam.quiz.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "numberClasses")
    private int numberClasses;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Subject() {
        super();
    }

    public Subject(String name, String description, int numberClasses, String code, Teacher teacher) {
        this.name = name;
        this.description = description;
        this.numberClasses = numberClasses;
        this.code = code;
        this.teacher = teacher;
    }

    public Subject(String name, String description, int numberClasses, String code) {
        this.name = name;
        this.description = description;
        this.numberClasses = numberClasses;
        this.code = code;
    }

    public Subject(long id, String name, String description, int numberClasses, String code, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberClasses = numberClasses;
        this.code = code;
        this.teacher = teacher;
    }

    public Subject(long id, String name, String description, int numberClasses, String code) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberClasses = numberClasses;
        this.code = code;
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getNumberClasses() {
        return numberClasses;
    }
    public void setNumberClasses(int numberClasses) {
        this.numberClasses = numberClasses;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}
