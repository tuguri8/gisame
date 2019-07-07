package me.gisa.api.naver.repository;

import me.gisa.api.naver.repository.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    Page<News> findByIdGreaterThanOrderByPubDateDesc(Long id, Pageable pageable);

//    @Modifying
//    @Query(value = "DELETE FROM News n1, News n2  " +
//        "WHERE n1.id > n2.id " +
//        "AND n1.id > 0 " +
//        "AND n1.title = n2.title " +
//        "AND n1.content = n2.content " +
//        "AND n1.webUrl = n2.webUrl")
//    void deleteByIdGreaterThan();
}
