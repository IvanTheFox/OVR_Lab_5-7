package com.not_a_team.university.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.not_a_team.university.Entities.User;
import com.not_a_team.university.Repositories.UserRepository;
import com.not_a_team.university.Services.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserRepository userRepository, UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String defaultPage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPath(HttpSession session, Model model, @RequestParam(required = false) boolean error) {
        if (userService.getUserBySession(session).isPresent()) {
            System.out.println("Found active session!");
            return "redirect:/profile";
        }

        return "login";
    }

    @PostMapping("/login")
    public String loginForm(HttpSession session, Model model, @RequestParam String username, @RequestParam String password) {
        if (!userService.checkLoginInfo(session, username, password)) {
            model.addAttribute("errorMessage", "Неправильный логин или пароль");
            return "login";
        }

        User user = userService.getUserByUsername(username).get();
        user.addSesseion(session);
        userService.saveUser(user);

        return "redirect:/profile";
    }
}
