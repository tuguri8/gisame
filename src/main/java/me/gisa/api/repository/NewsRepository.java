package me.gisa.api.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import me.gisa.api.controller.model.PageVO;
import me.gisa.api.repository.entity.KeywordType;
import me.gisa.api.repository.entity.News;
import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.repository.entity.QNews;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<List<News>> findAllByNewsType(NewsType newsType);
    Optional<List<News>> findAllByRegionCodeAndSearchKeywordAndNewsTypeAndPubDateBetween(String regionCode, KeywordType keywordType, NewsType newsType, LocalDate startDate, LocalDate endDate, Pageable pageable);

}
