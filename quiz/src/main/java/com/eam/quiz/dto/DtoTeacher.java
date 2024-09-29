package com.eam.quiz.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DtoTeacher {
    @NotBlank(message = "El Campo No Puede Estar Vacio")
    @Size(min = 10, message = "El Numero Debe Tener Una Cantidad Valida")
    private int phoneNumber;
    @NotBlank(message = "El Campo No Puede Estar Vacio")
    private String name;
    @NotBlank(message = "El Campo No Puede Estar Vacio")
    @Email
    private String email;

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

}