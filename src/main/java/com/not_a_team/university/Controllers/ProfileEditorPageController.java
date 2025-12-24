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
 * Класс-контроллер для страницы редактирования данных пользователей
 */
@Controller
public class ProfileEditorPageController {
    private UserService userService;

    /**
     * Конструктор, создающий объект-контроллер
     * @param userService
     */
    public ProfileEditorPageController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Обработчик перехода на страницу редактирования данных пользователя
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/editprofiles")
    public String profileEditorPage(HttpSession session, Model model) {
        Optional<User> _user = userService.getUserBySession(session);
        if (_user.isEmpty())
            return "redirect:/login";
        User user = _user.get();

        if (user.getRole() != Role.Admin)
            return "redirect:/index";

        model.addAttribute("user", new ResponseUser(user));

        return "editprofiles";
    }
}
