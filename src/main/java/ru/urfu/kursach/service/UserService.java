package ru.urfu.kursach.service;

import org.springframework.stereotype.Service;
import ru.urfu.kursach.dto.UserDto;
import ru.urfu.kursach.entity.Role;
import ru.urfu.kursach.entity.User;

import java.util.List;

@Service
public interface UserService {
    void SaveUser(UserDto user);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
}
