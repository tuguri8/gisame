package me.gisa.api.naver.repository;

import me.gisa.api.naver.repository.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByIdGreaterThan(Long id);

}
