package com.not_a_team.university.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.not_a_team.university.Entities.News;
import com.not_a_team.university.Repositories.NewsRepository;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    // Manage news
    public void saveNews(News news) {
        newsRepository.save(news);
    }
    public void deleteNews(News news) {
        newsRepository.deleteById(news.getId());
    }
    public void deleteNewsById(Long id) {
        newsRepository.deleteById(id);
    }
    // Get news
    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }
}
