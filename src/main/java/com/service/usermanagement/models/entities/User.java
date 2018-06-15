package com.service.usermanagement.models.entities;

import com.service.usermanagement.constants.Constants;
import com.service.usermanagement.models.dto.NewUserDto;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;
    private String fullName;
    private Date birthday;
    private String address;
    private String gender;

    public User() {
    }

    public User(NewUserDto newUserDto) throws ParseException {
        update(newUserDto);
    }

    public void update(NewUserDto newUserDto) throws ParseException {
        setFullName(newUserDto.getFullName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
        setBirthday(simpleDateFormat.parse(newUserDto.getBirthday()));
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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
