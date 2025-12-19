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
import com.not_a_team.university.Services.NewsService;
import com.not_a_team.university.Services.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
public class NewsInfoController {
    private NewsService newsService;
    private UserService userService;

    public NewsInfoController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping("/newsinfo/{id}")
    public ResponseEntity<News> getNewsInfo(@PathVariable("id") Long id) {
        Optional<News> news = newsService.getNewsById(id);

        if (news.isPresent())
            return ResponseEntity.ok(news.get());
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/prevnewsinfo/{id}")
    public ResponseEntity<News> getPreviousNews(@PathVariable("id") Long id) {
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

    @PostMapping(value = "/newnews", consumes = "multipart/form-data")
    public String publishNews(HttpSession session, @RequestParam Optional<List<MultipartFile>> files, @RequestParam String[] existingFiles, @RequestParam String title, @RequestParam String text) {
        Optional<User> _user = userService.getUserBySession(session);
        if (_user.isEmpty())
            return "redirect:/login";
        User user = _user.get();
        News news = new News(user.getId());
        newsService.saveNews(news);
        news.setText(text);
        news.setTitle(title);
        System.out.println(files.get());
        for (String picturePath : existingFiles) {
            news.addPicture(picturePath);
        }
        if(files.isPresent()){
            for (MultipartFile file : files.get()) {
                try {
                    System.out.println(file);
                    news.addPicture(file);
                } catch (IOException exception) {
                    System.out.println("Unable to save file to " + exception.getMessage());
                }
            }
        }
        newsService.saveNews(news);
        System.out.println(news.getId());
        return "Новость опубликована!";
    }

    @PostMapping(value = "/editnews", consumes = "multipart/form-data")
    public String editNews(HttpSession session, @RequestParam Long newsId, @RequestParam Optional<List<MultipartFile>> files, @RequestParam String[] existingFiles, @RequestParam String title, @RequestParam String text) {
        Optional<User> _user = userService.getUserBySession(session);
        if (_user.isEmpty())
            return "redirect:/login";

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
}
