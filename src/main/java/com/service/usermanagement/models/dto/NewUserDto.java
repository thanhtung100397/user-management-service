package com.service.usermanagement.models.dto;

import com.service.usermanagement.validation.date.Date;
import com.service.usermanagement.validation.gender_string.Gender;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class NewUserDto {
    @NotEmpty
    private String fullName;
    private LocalDate birthday;
    @NotEmpty
    private String address;
    @Gender
    private String userGender;

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

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}
