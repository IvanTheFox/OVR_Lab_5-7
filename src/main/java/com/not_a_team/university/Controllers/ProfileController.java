package com.not_a_team.university.Controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.not_a_team.university.Entities.ResponseUser;
import com.not_a_team.university.Entities.User;
import com.not_a_team.university.Services.UserService;

import jakarta.servlet.http.HttpSession;

/**
 * Класс-контроллер для страницы профиля
 */
@Controller
public class ProfileController {
    private UserService userService;

    /**
     * Конструктор, создающий объект-контроллер
     * @param userService - сервис для работы с пользователями
     */
    public ProfileController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Обработчик перехода на страницу профиля
     * @param session - активная сессия пользователя
     * @param model - модель для отображения динамической информации
     * @return - вид новой страницы
     */
    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        Optional<User> _user = userService.getUserBySession(session);
        if (_user.isEmpty())
            return "redirect:/login";
        
        User user = _user.get();
        model.addAttribute("user", new ResponseUser(user));

        return "profile";
    }

    /**
     * Обработчик запроса на установление аватара пользователем
     * @param session - активная сессия пользователя
     * @param model - модель для отображения динамической информации
     * @param file - файл изображения аватара
     * @return - вид новой страницы
     */
    @PostMapping("/profile/upload")
    public String avatarUpload(HttpSession session, Model model, MultipartFile file) {
        Optional<User> _user = userService.getUserBySession(session);
        if (_user.isEmpty())
            return "redirect:/login";
        User user = _user.get();

        try {
            user.setAvatar(file);
            userService.saveUserAndCommit(user);
        } catch (IOException exception) {
            System.out.println("Failed to upload avatar image.");
            model.addAttribute("fileError", exception.getMessage());
        }

        return "redirect:/profile";
    }

    /**
     * Обработчик запроса выхода из аккаунта
     * @param session - активная сессия пользователя
     * @return - вид новой страницы
     */
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        Optional<User> _user = userService.getUserBySession(session);
        if (_user.isEmpty())
            return "redirect:/login";
        
        User user = _user.get();
        try {
            user.removeSession(session);
            userService.saveUser(user);
        } catch (User.NoSessionFound exception) {
            System.out.println(exception.getMessage());
        };

        return "redirect:/login";
    }
}
