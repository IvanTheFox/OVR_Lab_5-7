package com.not_a_team.university.Controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.not_a_team.university.Entities.News;
import com.not_a_team.university.Entities.User;
import com.not_a_team.university.Enums.Role;
import com.not_a_team.university.Services.NewsService;
import com.not_a_team.university.Services.UserService;

import jakarta.servlet.http.HttpSession;

/**
 * Класс для обработки запросов, связанных с новостями
 */
@RestController
public class NewsInfoController {
    private NewsService newsService;
    private UserService userService;

    /**
     * Конструктор, создающий объект-контроллер
     * @param newsService - сервис для работы с новостями
     * @param userService - сервис для работы с пользователями
     */
    public NewsInfoController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    /**
     * Обработчик запроса на получение информации о новости
     * @param id - идентификатор новости
     * @return - новость
     */
    @GetMapping("/newsinfo/{id}")
    public ResponseEntity<News> getNewsInfo(@PathVariable("id") Long id) {
        Optional<News> news = newsService.getNewsById(id);

        if (news.isPresent())
            return ResponseEntity.ok(news.get());
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * Обработчик запроса на получение предыдущей новости для текущей
     * @param session - активная сессия пользователя
     * @param id - идентификатор текущей новости
     * @return - предыдущая новость
     */
    @GetMapping("/prevnewsinfo/{id}")
    public ResponseEntity<News> getPreviousNews(HttpSession session, @PathVariable("id") Long id) {
        Optional<User> _user = userService.getUserBySession(session);
        if (_user.isEmpty())
            return ResponseEntity.notFound().build();
        
        Optional<News> news;
        if (id.equals(-1L))
            news = newsService.getLatestNews();
        else
            news = newsService.getPreviousNews(id);

        if (news.isPresent())
            return ResponseEntity.ok(news.get());
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * Обработчик запроса на публикацию новости
     * @param session - активная сессия пользователя
     * @param files - изображения в новости
     * @param existingFiles - уже сохранённые на сервере изображения в новости
     * @param title - заголовок новости
     * @param text - текст новости
     * @return - успех публикации (в формате текста)
     */
    @PostMapping(value = "/newnews", consumes = "multipart/form-data")
    public String publishNews(HttpSession session, @RequestParam Optional<List<MultipartFile>> files, @RequestParam String[] existingFiles, @RequestParam String title, @RequestParam String text) {
        Optional<User> _user = userService.getUserBySession(session);
        if (_user.isEmpty())
            return "redirect:/login";
        User user = _user.get();
        if (user.getRole() == Role.User)
            return "redirect:/index";

        News news = new News(user.getId());
        newsService.saveNews(news);
        news.setText(text);
        news.setTitle(title);
        for (String picturePath : existingFiles) {
            news.addPicture(picturePath);
        }
        if(files.isPresent()){
            for (MultipartFile file : files.get()) {
                try {
                    news.addPicture(file);
                } catch (IOException exception) {
                    System.out.println("Unable to save file to " + exception.getMessage());
                }
            }
        }
        newsService.saveNews(news);
        return "Новость опубликована!";
    }

    /**
     * Обработчик запроса на редактирование новости
     * @param session - активная сессия пользователя
     * @param newsId - идентификатор новости
     * @param files - изображения в новости
     * @param existingFiles - уже сохранённые на сервере изображения в новости
     * @param title - заголовок новости
     * @param text - текст новости
     * @return - успех редактирования (в формате строки)
     */
    @PostMapping(value = "/editnews", consumes = "multipart/form-data")
    public String editNews(HttpSession session, @RequestParam Long newsId, @RequestParam Optional<List<MultipartFile>> files, @RequestParam String[] existingFiles, @RequestParam String title, @RequestParam String text) {
        Optional<User> _user = userService.getUserBySession(session);
        if (_user.isEmpty())
            return "redirect:/login";
        User user = _user.get();
        if (user.getRole() == Role.User)
            return "redirect:/index";

        Optional<News> _news = newsService.getNewsById(newsId);
        if (_news.isEmpty())
            return "Новость не найдена!";

        News news = _news.get();
        news.setText(text);
        news.setTitle(title);
        for (String picturePath : existingFiles) {
            news.addPicture(picturePath);
        }

        if(files.isPresent()){
            for (MultipartFile file : files.get()) {
                try {
                    news.addPicture(file);
                } catch (IOException exception) {
                    System.out.println("Unable to save file to " + exception.getMessage());
                }
            }
        }

        newsService.saveNews(news);
        return "Новость отредактирована!";
    }

    /**
     * Обработчик запроса на удаление новости
     * @param id - идентификатор новости
     * @return - результат удаления новости
     */
    @PostMapping("/deletenews/{id}")
    public String postMethodName(@PathVariable("id") Long id) {
        newsService.deleteNewsById(id);
        return "deleted";
    }
}
