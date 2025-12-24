package com.not_a_team.university.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.not_a_team.university.Entities.News;
import com.not_a_team.university.Repositories.NewsRepository;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    /**
     * Конструктор сервиса
     * @param newsRepository - репозитория новостей
     */
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    /**
     * Метод сохранения новости в репозиторий
     * @param news - новость
     */
    public void saveNews(News news) {
        newsRepository.save(news);
    }

    /**
     * Метод удаления новости из репозитория
     * @param news - новость
     */
    public void deleteNews(News news) {
        newsRepository.deleteById(news.getId());
    }
    
    /**
     * Метод удаления новости из репозитория
     * @param id - идентификатор новости
     */
    public void deleteNewsById(Long id) {
        newsRepository.deleteById(id);
    }

    /**
     * Метод получения новости по идентификатору
     * @param id - идентификатор
     * @return - новость
     */
    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }

    /**
     * Метод получения всех новостей
     * @return - множество новостей
     */
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    /**
     * Метод получения последних опубликованных новостей
     * @return - новости
     */
    public Optional<News> getLatestNews() {
        Optional<News> latestNews = Optional.empty();

        ArrayList<News> news = new ArrayList<News>(getAllNews());
        for (News n : news) {
            if (latestNews.isEmpty() || latestNews.get().getPublishTime() < n.getPublishTime())
                latestNews = Optional.of(n);
        }

        return latestNews;
    }

    /**
     * Метод получения новостей, предшествующий текущим
     * @param id - идентификатор текущих новостей
     * @return - следующие новости
     */
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
            if (n.getId().equals(id))
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
