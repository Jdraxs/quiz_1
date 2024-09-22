package com.eam.quiz.dto;

import com.eam.quiz.model.Teacher;

public class DtoSubject {

    private String name;
    private String description;
    private int numberClasses;
    private String code;
    private Teacher teacher;

    public DtoSubject() {
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

    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}