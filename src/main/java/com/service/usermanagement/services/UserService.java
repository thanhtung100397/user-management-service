package com.service.usermanagement.services;

import com.service.usermanagement.dao.UserRepository;
import com.service.usermanagement.models.dto.ErrorDto;
import com.service.usermanagement.models.dto.NewUserDto;
import com.service.usermanagement.models.dto.PageDto;
import com.service.usermanagement.models.dto.UserDto;
import com.service.usermanagement.models.entities.User;
import com.service.usermanagement.utils.PageRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<PageDto<UserDto>> getUsers(String sortBy, String sortType,
                                                     Integer pageIndex, Integer pageSize) {
        Pageable pageable = new PageRequestBuilder()
                .sort(sortBy, sortType)
                .page(pageIndex, pageSize)
                .build();
        Page<UserDto> queryResult = userRepository.getListUserDto(pageable);
        PageDto<UserDto> responseBody = new PageDto<>(queryResult);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    public ResponseEntity<UserDto> getUser(String userID) {
        UserDto queryResult = userRepository.getUserDto(userID);
        return new ResponseEntity<>(queryResult, HttpStatus.OK);
    }

    public ResponseEntity createNewUser(NewUserDto newUserDto) {
        try {
            User user = new User(newUserDto);
            userRepository.save(user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ParseException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity updateUser(String userID, NewUserDto newUserDto) {
        User user = userRepository.findFirstById(userID);
        if(user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        try {
            user.update(newUserDto);
            userRepository.save(user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ParseException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity deleteUser(String userID) {
        User user = userRepository.findFirstById(userID);
        if(user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        userRepository.delete(user);
        return new ResponseEntity(HttpStatus.OK);
    }

}
