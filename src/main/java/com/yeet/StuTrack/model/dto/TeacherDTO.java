package com.yeet.StuTrack.model.dto;

import javax.validation.constraints.NotNull;

public class TeacherDTO {

    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
