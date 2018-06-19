package com.service.usermanagement.models.dto;

import com.service.usermanagement.models.entities.Gender;
import com.service.usermanagement.validation.date.Date;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class NewUserDto {
    @NotEmpty
    private String fullName;
    private LocalDate birthday;
    @NotEmpty
    private String address;
    private Gender gender;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
