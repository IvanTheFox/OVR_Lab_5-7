package com.not_a_team.university.Controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.not_a_team.university.Entities.News;
import com.not_a_team.university.Services.NewsService;

@RestController
public class NewsController {
    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/newsinfo/*")
    public ResponseEntity<News> getNewsInfo(@RequestParam("id") Long id) {
        Optional<News> news = newsService.getNewsById(id);

        if (news.isPresent())
            return ResponseEntity.ok(news.get());
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/prevnewsinfo/*")
    public ResponseEntity<News> getPreviousNews(@RequestParam("id") Long id) {
        Optional<News> news;
        if (id == -1)
            news = newsService.getLatestNews();
        else
            news = newsService.getPreviousNews(id);

        if (news.isPresent())
            return ResponseEntity.ok(news.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/editarticle")
    public void publishArticle() {
        
    }
}
