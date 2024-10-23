package ru.urfu.kursach.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.urfu.kursach.entity.User;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    private boolean roleAdmin = false;
    private boolean roleUser = false;
    private boolean roleReadOnly = false;

    public UserDto(User user) {
        var parts = user.getName().split(" ");
        firstName = parts[0];
        lastName = parts[1];
        email = user.getEmail();
        password = user.getPassword();
        id = user.getId();
        roleAdmin = user.getRoles().stream().anyMatch(role -> Objects.equals(role.getName(), "ROLE_ADMIN"));
        roleUser = user.getRoles().stream().anyMatch(role -> Objects.equals(role.getName(), "ROLE_USER"));
        roleReadOnly = user.getRoles().stream().anyMatch(role -> Objects.equals(role.getName(), "ROLE_READ_ONLY"));
    }
}