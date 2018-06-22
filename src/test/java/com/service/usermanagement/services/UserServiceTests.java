package com.service.usermanagement.services;

import com.service.usermanagement.BaseMockitoJUnitTests;
import com.service.usermanagement.constants.Constants;
import com.service.usermanagement.constants.ResponseMessage;
import com.service.usermanagement.models.dto.MessageDto;
import com.service.usermanagement.models.dto.NewUserDto;
import com.service.usermanagement.models.dto.PageDto;
import com.service.usermanagement.models.dto.UserDto;
import com.service.usermanagement.models.entities.Gender;
import com.service.usermanagement.models.entities.User;
import com.service.usermanagement.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class UserServiceTests extends BaseMockitoJUnitTests {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void testGetUsers_Ok() {
        int pageIndex = 0;
        int pageSize = Constants.MAX_PAGE_SIZE;

        when(userRepository.getListUserDto(any(Pageable.class)))
                .thenAnswer((Answer<Page<UserDto>>) invocation -> {
                    Pageable pageable = invocation.getArgument(0);
                    List<UserDto> usersDto = new ArrayList<>();
                    usersDto.add(new UserDto("ID_1", "Full Name 1",
                            LocalDate.of(2000, 1, 1), "Address 1", Gender.MALE));
                    usersDto.add(new UserDto("ID_2", "Full Name 2",
                            LocalDate.of(2000, 2, 2), "Address 2", Gender.FEMALE));
                    return new PageImpl<>(usersDto, pageable, usersDto.size());
                });

        ResponseEntity<PageDto<UserDto>> response = userService.getUsers(null, null, pageIndex, pageSize);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(1, response.getBody().getTotalPage());
        Assert.assertEquals(pageIndex, response.getBody().getPageIndex());
        Assert.assertEquals(pageSize, response.getBody().getPageSize());
        Assert.assertEquals(2, response.getBody().getItems().size());
        Assert.assertEquals(response.getBody().getTotalItem(), response.getBody().getItems().size());
    }

    @Test
    public void testGetUser_Ok() {
        String userID = "ID_1";
        when(userRepository.getUserDto(any(String.class)))
                .thenAnswer((Answer<UserDto>) invocation -> {
                    String queryUserID = invocation.getArgument(0);
                    return new UserDto(queryUserID, "Full Name 1",
                            LocalDate.of(2000, 1, 1), "Address 1", Gender.MALE);
                });
        ResponseEntity response = userService.getUser(userID);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertThat(response.getBody(), instanceOf(UserDto.class));
        Assert.assertEquals(userID, ((UserDto) response.getBody()).getId());
    }

    @Test
    public void testGetUser_NotFound() {
        String wrongUserID = "WRONG_ID";
        when(userRepository.getUserDto(any(String.class)))
                .thenAnswer((Answer<UserDto>) invocation -> {
                    String userID = invocation.getArgument(0);
                    if (userID == null || wrongUserID.equals(userID)) {
                        return null;
                    }
                    return new UserDto(userID, "Full Name 1",
                            LocalDate.of(2000, 1, 1), "Address 1", Gender.MALE);
                });
        ResponseEntity response = userService.getUser(wrongUserID);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertThat(response.getBody(), instanceOf(MessageDto.class));
        Assert.assertEquals(ResponseMessage.USER_NOT_FOUND, ((MessageDto) response.getBody()).getMessage());
    }

    @Test
    public void testCreateNewUser() {
        NewUserDto newUserDto = new NewUserDto();
        newUserDto.setFullName("Full Name");
        newUserDto.setAddress("Address");
        newUserDto.setBirthday(LocalDate.of(2000, 1, 1));
        newUserDto.setGender(Gender.MALE);

        User user = new User();

        when(userRepository.save(any(User.class)))
                .thenAnswer((Answer<Void>) invocation -> {
                    User newUser = invocation.getArgument(0);
                    user.setId("ID_1");
                    user.setFullName(newUser.getFullName());
                    user.setAddress(newUser.getAddress());
                    user.setBirthday(newUser.getBirthday());
                    user.setGender(newUser.getGender());
                    return null;
                });

        ResponseEntity responseEntity = userService.createNewUser(newUserDto);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Assert.assertNotNull(user.getId());
        Assert.assertEquals("ID_1", user.getId());

        Assert.assertNotNull(user.getFullName());
        Assert.assertEquals(newUserDto.getFullName(), user.getFullName());

        Assert.assertNotNull(user.getAddress());
        Assert.assertEquals(newUserDto.getAddress(), user.getAddress());

        Assert.assertNotNull(user.getBirthday());
        Assert.assertEquals(newUserDto.getBirthday(), user.getBirthday());

        Assert.assertNotNull(user.getGender());
        Assert.assertEquals(newUserDto.getGender(), user.getGender());
    }

    @Test
    public void testUpdateUser_Ok() {
        String userID = "ID_1";

        User user = new User();
        user.setId(userID);
        user.setFullName("Old Name");
        user.setAddress("Old Address");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        user.setGender(Gender.MALE);

        NewUserDto newUserDto = new NewUserDto();
        newUserDto.setFullName("New Name");
        newUserDto.setAddress("Address");
        newUserDto.setBirthday(LocalDate.of(2002, 2, 2));
        newUserDto.setGender(Gender.FEMALE);

        when(userRepository.findFirstById(any(String.class)))
                .thenAnswer((Answer<User>) invocation -> new User());

        when(userRepository.save(any(User.class)))
                .thenAnswer((Answer<Void>) invocation -> {
                    User updatedUser = invocation.getArgument(0);
                    user.setId(userID);
                    user.setFullName(updatedUser.getFullName());
                    user.setBirthday(updatedUser.getBirthday());
                    user.setAddress(updatedUser.getAddress());
                    user.setGender(updatedUser.getGender());
                    return null;
                });

        ResponseEntity response = userService.updateUser(userID, newUserDto);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(userID, user.getId());
        Assert.assertEquals(newUserDto.getFullName(), user.getFullName());
        Assert.assertEquals(newUserDto.getBirthday(), user.getBirthday());
        Assert.assertEquals(newUserDto.getAddress(), user.getAddress());
        Assert.assertEquals(newUserDto.getGender(), user.getGender());
    }

    @Test
    public void testUpdateUser_NotFound() {
        String wrongUserID = "WRONG_ID";
        NewUserDto newUserDto = new NewUserDto();

        when(userRepository.findFirstById(any(String.class)))
                .thenAnswer((Answer<User>) invocation -> {
                    String id = invocation.getArgument(0);
                    if (id == null || wrongUserID.equals(id)) {
                        return null;
                    }
                    return new User();
                });

        ResponseEntity response = userService.updateUser(wrongUserID, newUserDto);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertNotNull(response.getBody());
        Assert.assertThat(response.getBody(), instanceOf(MessageDto.class));
        Assert.assertEquals(ResponseMessage.USER_NOT_FOUND, ((MessageDto) response.getBody()).getMessage());
    }

    @Test
    public void testDeleteUser() {
        String wrongUserID = "WRONG_ID";
        String userID = "ID_1";

        boolean[] userExists = new boolean[]{true};

        when(userRepository.findFirstById(any(String.class)))
                .thenAnswer((Answer<User>) invocation -> {
                    String id = invocation.getArgument(0);
                    if (id == null || wrongUserID.equals(id)) {
                        return null;
                    }
                    return new User();
                });

        doAnswer(invocation -> {
            userExists[0] = false;
            return null;
        }).when(userRepository).delete(any(User.class));

        //delete user with wrong id
        ResponseEntity notFoundResponse = userService.deleteUser(wrongUserID);
        Assert.assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode());
        Assert.assertNotNull(notFoundResponse.getBody());
        Assert.assertThat(notFoundResponse.getBody(), instanceOf(MessageDto.class));
        Assert.assertEquals(ResponseMessage.USER_NOT_FOUND, ((MessageDto) notFoundResponse.getBody()).getMessage());

        //delete user with correct id
        ResponseEntity okResponse = userService.deleteUser(userID);
        Assert.assertEquals(HttpStatus.OK, okResponse.getStatusCode());
        Assert.assertFalse(userExists[0]);
    }
}