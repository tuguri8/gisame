package me.gisa.api.repository.entity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<List<News>> findAllByNewsType(NewsType newsType);
    Optional<List<News>> findAllByRegionCodeAndSearchKeywordAndNewsTypeAndPubDateBetween(String regionCode, KeywordType keywordType, NewsType newsType, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
