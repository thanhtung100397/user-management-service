package com.service.usermanagement.models.entities;

import com.service.usermanagement.constants.Constants;
import com.service.usermanagement.models.dto.NewUserDto;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;
    private String fullName;
    private LocalDate birthday;
    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public User() {
    }

    public User(NewUserDto newUserDto) throws ParseException {
        update(newUserDto);
    }

    public void update(NewUserDto newUserDto) throws ParseException {
        setFullName(newUserDto.getFullName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
        setBirthday(newUserDto.getBirthday());
        setAddress(newUserDto.getAddress());
        setGender(newUserDto.getGender());
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