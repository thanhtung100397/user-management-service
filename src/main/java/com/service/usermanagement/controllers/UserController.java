package com.service.usermanagement.controllers;

import com.service.usermanagement.constants.Constants;
import com.service.usermanagement.models.dto.NewUserDto;
import com.service.usermanagement.models.dto.PageDto;
import com.service.usermanagement.models.dto.UserDto;
import com.service.usermanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<PageDto<UserDto>> getListUsers(@RequestParam(required = false) String sortBy,
                                                         @RequestParam(required = false) String sortType,
                                                         @RequestParam(required = false, defaultValue = "0") Integer pageIndex,
                                                         @RequestParam(required = false, defaultValue = Constants.MAX_PAGE_SIZE + "") Integer pageSize) {
        return userService.getUsers(sortBy, sortType, pageIndex, pageSize);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") String userID) {
        return userService.getUser(userID);
    }

    @PostMapping("/users")
    public ResponseEntity createNewUser(@RequestBody @Valid NewUserDto newUserDto) {
        return userService.createNewUser(newUserDto);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity updateUser(@PathVariable("id") String userID,
                                     @RequestBody @Valid NewUserDto newUserDto) {
        return userService.updateUser(userID, newUserDto);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String userID) {
        return userService.deleteUser(userID);
    }
}
