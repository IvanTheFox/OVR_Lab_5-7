package com.not_a_team.university.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.not_a_team.university.Entities.User;
import com.not_a_team.university.Services.UserService;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

/**
 * Класс-контроллер для страницы входа
 */
@Controller
public class LoginController {
    private final UserService userService;

    /**
     * Конструктор, создающий объект-контроллер
     * @param userService - сервис для работы с пользователями
     */
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Обработчик перехода в корень сайта
     * @return - вид страницы входа
     */
    @GetMapping("/")
    public String defaultPage() {
        return "redirect:/login";
    }

    /**
     * Обработчик перехода на страницу входа
     * @param session - активная сессия пользователя
     * @param model - модель для отображения динамической информации
     * @param error - возникла ли ошибка при предыдущем входе
     * @return - вид новой страницы
     */
    @GetMapping("/login")
    public String loginPath(HttpSession session, Model model, @RequestParam(required = false) boolean error) {
        if (userService.getUserBySession(session).isPresent()) {
            return "redirect:/profile";
        }

        return "login";
    }

    /**
     * Обработчик запроса на вход
     * @param session - активная сессия пользователя
     * @param model - модель для отображения динамической информации
     * @param username - имя, введённое пользователем
     * @param password - пароль, введённый пользователем
     * @return - вид новой страницы
     */
    @PostMapping("/login")
    public String loginForm(HttpSession session, Model model, @RequestParam String username, @RequestParam String password) {
        Optional<User> _user = userService.getUserBySession(session);
        if (_user.isPresent())
            return "redirect:/profile";
        
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
