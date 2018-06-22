package com.service.usermanagement.services;

import com.service.usermanagement.constants.ResponseMessage;
import com.service.usermanagement.repository.UserRepository;
import com.service.usermanagement.models.dto.MessageDto;
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
                                                     int pageIndex, int pageSize) {
        Pageable pageable = new PageRequestBuilder()
                .sort(sortBy, sortType)
                .page(pageIndex, pageSize)
                .build();
        Page<UserDto> queryResult = userRepository.getListUserDto(pageable);
        PageDto<UserDto> responseBody = new PageDto<>(queryResult);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    public ResponseEntity getUser(String userID) {
        UserDto queryResult = userRepository.getUserDto(userID);
        if (queryResult == null) {
            return new ResponseEntity<>(new MessageDto(ResponseMessage.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(queryResult, HttpStatus.OK);
    }

    public ResponseEntity createNewUser(NewUserDto newUserDto) {
        User user = new User(newUserDto);
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity updateUser(String userID, NewUserDto newUserDto) {
        User user = userRepository.findFirstById(userID);
        if (user == null) {
            return new ResponseEntity<>(new MessageDto(ResponseMessage.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        user.update(newUserDto);
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity deleteUser(String userID) {
        User user = userRepository.findFirstById(userID);
        if (user == null) {
            return new ResponseEntity<>(new MessageDto(ResponseMessage.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        userRepository.delete(user);
        return new ResponseEntity(HttpStatus.OK);
    }

}
