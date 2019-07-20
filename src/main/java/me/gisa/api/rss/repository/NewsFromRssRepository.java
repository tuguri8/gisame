package me.gisa.api.rss.repository;

import me.gisa.api.rss.repository.entity.NewsFromRss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsFromRssRepository extends JpaRepository<NewsFromRss, Long> {
    List<NewsFromRss> findByRegionNameContaining(String regionName);

    List<NewsFromRss> findAll();
}
