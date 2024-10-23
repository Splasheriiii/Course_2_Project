package ru.urfu.kursach.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.urfu.kursach.dto.UserDto;
import ru.urfu.kursach.entity.User;
import ru.urfu.kursach.service.UserService;

import java.util.List;

@Controller
public class SecurityController {
    private UserService userService;
    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home() { return "index"; }

    @GetMapping("/about")
    public String about() { return "about"; }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto dto = new UserDto();
        dto.setRoleReadOnly(true);
        model.addAttribute("user", dto);
        return "register";
    }

    @GetMapping("/users")
    public String users(Model model) {
        var users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/edit/{email}")
    public ModelAndView edit(@PathVariable String email) {
        var mav = new ModelAndView("user-form");
        var user = userService.findUserByEmail(email);
        mav.addObject("user", new UserDto(user));
        return mav;
    }

    @PostMapping("/users/save")
    public String registration(@Valid @ModelAttribute("user") UserDto dto, BindingResult result) {
        if (!result.hasErrors()) {
            userService.SaveUser(dto);
        }
        return "redirect:/users";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto dto, BindingResult result, Model model) {
        User existingUser = userService.findUserByEmail(dto.getEmail());
        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", "На этот адрес уже зарегистрирована учетная запись");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", dto);
            return "/register";
        }

        userService.SaveUser(dto);
        return "redirect:/register?success";
    }
}
