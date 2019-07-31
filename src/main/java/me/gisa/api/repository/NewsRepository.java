package me.gisa.api.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import me.gisa.api.controller.model.PageVO;
import me.gisa.api.repository.entity.News;
import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.repository.entity.QNews;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, QuerydslPredicateExecutor<News> {

    Optional<List<News>> findAllByNewsType(NewsType newsType);

    public default Predicate makePredicate(PageVO page){

        System.out.println(page.toString());

        BooleanBuilder builder = new BooleanBuilder();
        QNews news = QNews.news;

        if(!StringUtils.isEmpty(page.getRegionCode()))
            builder.and(news.regionCode.eq(page.getRegionCode()));

        if(!Objects.isNull(page.getStartDate()))
            builder.and(news.pubDate.after(page.getStartDate().minusDays(1)));

        if(!Objects.isNull(page.getEndDate()))
            builder.and(news.pubDate.before(page.getEndDate().plusDays(1)));

        if(!Objects.isNull(page.getNewsType()))
            builder.and(news.newsType.eq(page.getNewsType()));

        if(!Objects.isNull(page.getSearchKeyword()))
            builder.and(news.searchKeyword.eq(page.getSearchKeyword()));

        
        return builder;
    }

}
