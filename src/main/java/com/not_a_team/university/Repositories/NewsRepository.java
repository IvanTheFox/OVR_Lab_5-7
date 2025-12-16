package com.not_a_team.university.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.not_a_team.university.Entities.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findById(Long id);
}
