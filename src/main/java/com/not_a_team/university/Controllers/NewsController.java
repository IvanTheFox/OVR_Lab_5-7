package com.not_a_team.university.Controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.not_a_team.university.Entities.ResponseUser;
import com.not_a_team.university.Entities.User;
import com.not_a_team.university.Enums.Role;
import com.not_a_team.university.Services.NewsService;
import com.not_a_team.university.Services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class NewsController {
    private NewsService newsService;
    private UserService userService;

    public NewsController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

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

    @PostMapping("/newnews")
    public String publishNews() {
        
    }

    @PostMapping("/editnews")
    public String editNews() {

    }
}
