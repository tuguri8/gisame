package me.gisa.api.service.gisame;

import com.google.common.collect.Lists;
import me.gisa.api.controller.model.PageVO;
import me.gisa.api.repository.NewsRepository;
import me.gisa.api.repository.entity.News;
import me.gisa.api.service.model.NewsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GisameServicempl implements GisameService {

    private final NewsRepository newsRepository;

    public GisameServicempl(NewsRepository newsRepository) {this.newsRepository = newsRepository;}

    @Override
    public List<NewsModel> getNewsList(PageVO pageVO) {

        Page<News> newsList = newsRepository.findAll(newsRepository.makePredicate(pageVO), pageVO.makePageable(0, "id"));
        return newsList.getContent().stream().map(news -> transform(news)).collect(Collectors.toList());
    }

    private NewsModel transform(News news){
        NewsModel newsModel = new NewsModel();
        newsModel.setTitle(news.getTitle());
        newsModel.setContent(news.getContent());
        newsModel.setNewsType(news.getNewsType().name());
        newsModel.setOriginalLink(news.getOriginalLink());
        newsModel.setPubDate(news.getPubDate());
        newsModel.setRegionCode(news.getRegionCode());
        newsModel.setSearchKeyword(news.getSearchKeyword().getKeyword());
        newsModel.setSubLink(news.getSubLink());
        newsModel.setSummary(news.getSummary());
        return newsModel;
    }

}
