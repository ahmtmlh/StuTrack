package com.yeet.StuTrack.model.dto;

import javax.validation.constraints.NotNull;

public class StudentDTO {

    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String number;
    @NotNull
    private Integer year;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
