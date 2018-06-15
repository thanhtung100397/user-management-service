package com.service.usermanagement.models.dto;

import com.service.usermanagement.validation.date.Date;
import com.service.usermanagement.validation.gender_string.Gender;

import javax.validation.constraints.NotEmpty;

public class NewUserDto {
    @NotEmpty
    private String fullName;
    @Date
    private String birthday;
    @NotEmpty
    private String address;
    @Gender
    private String gender;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
