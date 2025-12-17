package com.not_a_team.university.Services;

import java.util.ArrayList;
import java.util.List;
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
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }
    public Optional<News> getLatestNews() {
        Optional<News> latestNews = Optional.empty();

        ArrayList<News> news = new ArrayList<News>(getAllNews());
        for (News n : news) {
            if (latestNews.isEmpty() || latestNews.get().getPublishTime() < n.getPublishTime())
                latestNews = Optional.of(n);
        }

        return latestNews;
    }
    public Optional<News> getPreviousNews(Long id) {
        Optional<News> _currentNews = getNewsById(id);
        if (_currentNews.isEmpty())
            return Optional.empty();

        News currentNews = _currentNews.get();
        Long currentTime = currentNews.getPublishTime();
        Optional<News> prevNews = Optional.empty();

        ArrayList<News> news = new ArrayList<News>(getAllNews());
        for (News n : news) {
            Long nPublishTime = n.getPublishTime();
            
            if (n.getId() == id)
                continue;

            if (nPublishTime > currentTime)
                continue;

            if (prevNews.isEmpty()) {
                prevNews = Optional.of(n);
                continue;
            }

            if (currentTime - nPublishTime < currentTime - prevNews.get().getPublishTime())
                prevNews = Optional.of(n);
        }

        return prevNews;
    }
}
