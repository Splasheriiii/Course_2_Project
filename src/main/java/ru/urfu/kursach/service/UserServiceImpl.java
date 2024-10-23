package ru.urfu.kursach.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.urfu.kursach.dto.UserDto;
import ru.urfu.kursach.entity.Role;
import ru.urfu.kursach.entity.User;
import ru.urfu.kursach.repository.RoleRepository;
import ru.urfu.kursach.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String[] ROLES = { "ROLE_ADMIN", "ROLE_USER", "ROLE_READ_ONLY" };

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        for (var role: ROLES) {
            if (roleRepository.findByName(role).isEmpty()) {
                roleRepository.save(Role.builder().name(role).build());
            }
        }
    }

    @Override
    public void SaveUser(UserDto user) {
        var u = User.builder()
                .id(user.getId())
                .name(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .password(user.getId() == null ? passwordEncoder.encode(user.getPassword()) : user.getPassword())
                .roles(Stream.of(roleRepository.findByName(user.isRoleAdmin() ? "ROLE_ADMIN" : ""),
                                 roleRepository.findByName(user.isRoleUser() ? "ROLE_USER" : ""),
                                 roleRepository.findByName(user.isRoleReadOnly() ? "ROLE_READ_ONLY" : ""))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList())
                .build();
        userRepository.save(u);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }
}
