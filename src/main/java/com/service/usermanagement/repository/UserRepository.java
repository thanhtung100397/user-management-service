package com.service.usermanagement.repository;

import com.service.usermanagement.models.dto.UserDto;
import com.service.usermanagement.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {
    User findFirstById(String userID);

    @Query("select new com.service.usermanagement.models.dto.UserDto(u) from User u")
    Page<UserDto> getListUserDto(Pageable pageable);

    @Query("select new com.service.usermanagement.models.dto.UserDto(u) from User u where u.id = ?1")
    UserDto getUserDto(String userID);
}
