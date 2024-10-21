package ru.urfu.kursach.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.urfu.kursach.dto.UserDto;
import ru.urfu.kursach.entity.User;
import ru.urfu.kursach.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(RoleService roleService, UserRepository userRepository/*, PasswordEncoder passwordEncoder*/) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); /*passwordEncoder*/;
    }

    @Override
    public void UpdateRole(UserDto dto, boolean isPromotion) {
        var user = userRepository.findByEmail(dto.getEmail());
        if (user != null) {
            var role = roleService.changeRole(dto.getRights(), isPromotion);
            if (role.isPresent()) {
                user.setRole(role.get());
                userRepository.save(user);
            }
        }
    }

    @Override
    public void SaveUser(UserDto user) {
        User new_user = new User();
        new_user.setName(user.getFirstName() + " " + user.getLastName());
        new_user.setEmail(user.getEmail());
        new_user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Первый пользователь автоматически становится админом
        new_user.setRole(roleService.getOrCreateRoleByRights(roleService.isAdminExists()
                ? RoleService.MIN_RIGHTS
                : RoleService.MAX_RIGHTS));
        userRepository.save(new_user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserDto).collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        String[] name_parts = user.getName().split(" ");
        dto.setFirstName(name_parts[0]);
        dto.setLastName(name_parts[1]);
        dto.setEmail(user.getEmail());
        return dto;
    }
}
