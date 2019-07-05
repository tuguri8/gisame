package me.gisa.api.naver.repository;

import me.gisa.api.naver.repository.entity.NaverNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NaverNewsRepository extends JpaRepository<NaverNews, Long> {

    List<NaverNews> findByIdGreaterThan(Long id);

}
