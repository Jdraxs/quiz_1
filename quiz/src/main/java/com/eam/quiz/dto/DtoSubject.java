package com.eam.quiz.dto;

import jakarta.validation.constraints.*;
import com.eam.quiz.model.Teacher;

public class DtoSubject {

    @NotBlank(message = "El Campo No Puede Estar Vacio")
    private String name;
    @NotBlank(message = "El Campo No Puede Estar Vacio")
    private String description;
    @NotBlank(message = "El Campo No Puede Estar Vacio")
    private int numberClasses;
    @NotBlank(message = "El Campo No Puede Estar Vacio")
    @Size(min = 6, message = "El Codigo Debe Constar De Minimo 6 Caracteres")
    private String code;
    @NotBlank(message = "El Campo No Puede Estar Vacio")
    private long teacherId;

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

    public long getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }
}