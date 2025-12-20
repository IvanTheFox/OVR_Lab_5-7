package com.not_a_team.university.Controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.not_a_team.university.Entities.User;
import com.not_a_team.university.Enums.Role;
import com.not_a_team.university.Misc.RegisterInfoChecker;
import com.not_a_team.university.Repositories.UserRepository;
import com.not_a_team.university.Services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RegisterController {
    private final UserService userService;
    
    public RegisterController(UserRepository userRepository, UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(HttpSession session) {
        Optional<User> _user = userService.getUserBySession(session);
        if (_user.isPresent())
            return "redirect:/profile";
        
        return "register";
    }

    @PostMapping("/register")
    public String registerForm(HttpSession session, Model model, @RequestParam String username, @RequestParam String password, @RequestParam int role) {
        Optional<User> _user = userService.getUserBySession(session);
        if (_user.isPresent())
            return "redirect:/profile";
        
        boolean hasErrorOccured = false;
        if (!RegisterInfoChecker.checkUsername(username)) {
            model.addAttribute("usernameError", "Имя пользователя должно быть длиной 3 - 20 символов и содержать любые буквы или цифры");
            hasErrorOccured = true;
        }
        if (userService.getUserByUsername(username).isPresent()) {
            model.addAttribute("usernameError", "Введённое имя пользователя уже занято");
            hasErrorOccured = true;
        }
        if (!RegisterInfoChecker.checkPassword(password)) {
            model.addAttribute("passwordError", "Пароль должен быть длиной 5-20 символов и содержать любые буквы и цифры");
            hasErrorOccured = true;
        }

        if (hasErrorOccured)
            return "register";

        User user = new User(username, password, Role.getRoleFromPermLevel(role), session);
        userService.saveUser(user);

        return "redirect:/profile";
    }
}
