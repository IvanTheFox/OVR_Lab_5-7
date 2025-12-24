package com.not_a_team.university.Controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.not_a_team.university.Entities.ResponseUser;
import com.not_a_team.university.Entities.User;
import com.not_a_team.university.Enums.Role;
import com.not_a_team.university.Services.UserService;

import jakarta.servlet.http.HttpSession;

/**
 * Класс-контроллер для страницы редактирования новостей
 */
@Controller
public class NewsController {
    private UserService userService;

    /**
     * Конструктор, создающий объект-контроллер
     * @param userService - сервис для работы с пользователями
     */
    public NewsController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Обработчик перехода на страницу редактирования новостей
     * @param session - активная сессия пользователя
     * @param model - модель для отображения динамической информации
     * @return - вид новой страницы
     */
    @GetMapping("/editarticle")
    public String editArticlePage(HttpSession session, Model model) {
        Optional<User> _user = userService.getUserBySession(session);
        if (_user.isEmpty())
            return "redirect:/login";
        User user = _user.get();

        if (user.getRole() == Role.User) {
            return "redirect:/index";
        }

        model.addAttribute("user", new ResponseUser(user));

        return "editarticle";
    }
}
