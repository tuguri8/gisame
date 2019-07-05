package me.gisa.api.naver.service;

import me.gisa.api.datatool.naver.NaverClientProperties;
import me.gisa.api.naver.service.model.NewsResponse;
import me.gisa.api.naver.repository.NewsRepository;
import me.gisa.api.naver.repository.entity.News;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Configuration
@EnableConfigurationProperties({NaverClientProperties.class})
public class NaverNewsServiceImpl implements NaverNewsService {

    private final NewsRepository naverNewsRepository;

    public NaverNewsServiceImpl(NewsRepository naverNewsRepository) {this.naverNewsRepository = naverNewsRepository;}

    @Override
    public List<NewsResponse> getNewsList() {
        List<NewsResponse> newsResponseList = new ArrayList<>();
        naverNewsRepository.findByIdGreaterThan(0L).forEach(news ->{
            newsResponseList.add(transform(news));
        });
        return newsResponseList;
    }

    private NewsResponse transform(News news){
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setTitle(news.getTitle());
        newsResponse.setContent(news.getContent());
        newsResponse.setPath(news.getPath());
        newsResponse.setWebUrl(news.getWebUrl());
        return newsResponse;
    }

}
