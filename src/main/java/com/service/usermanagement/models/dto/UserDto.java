package com.service.usermanagement.models.dto;

import com.service.usermanagement.models.entities.Gender;
import com.service.usermanagement.models.entities.User;

import java.time.LocalDate;

public class UserDto {
    private String id;
    private String fullName;
    private LocalDate birthday;
    private String address;
    private Gender gender;

    public UserDto(User user) {
        setId(user.getId());
        setFullName(user.getFullName());
        setBirthday(user.getBirthday());
        setAddress(user.getAddress());
        setGender(user.getGender());
    }

    public UserDto(String id, String fullName, LocalDate birthday, String address, Gender gender) {
        this.id = id;
        this.fullName = fullName;
        this.birthday = birthday;
        this.address = address;
        this.gender = gender;
    }

    public UserDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
