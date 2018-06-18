package com.service.usermanagement.models.dto;

import com.service.usermanagement.models.entities.Gender;
import com.service.usermanagement.validation.date.Date;

import javax.validation.constraints.NotEmpty;

public class NewUserDto {
    @NotEmpty
    private String fullName;
    @Date
    private String birthday;
    @NotEmpty
    private String address;
    private Gender gender;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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
