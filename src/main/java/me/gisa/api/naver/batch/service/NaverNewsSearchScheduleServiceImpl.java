package me.gisa.api.naver.batch.service;

import me.gisa.api.datatool.naver.NaverClient;
import me.gisa.api.datatool.naver.NaverClientProperties;
import me.gisa.api.datatool.naver.model.v1.V1NaverNewsItems;
import me.gisa.api.datatool.naver.model.v1.V1NaverNewsResponse;
import me.gisa.api.naver.repository.NewsRepository;
import me.gisa.api.naver.repository.entity.News;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@Configuration
@EnableConfigurationProperties({NaverClientProperties.class})
public class NaverNewsSearchScheduleServiceImpl implements NaverNewsSearchScheduleService {

    private final NaverClient naverClient;
    private final NaverClientProperties naverClientProperties;
    private final NewsRepository newsRepository;

    public NaverNewsSearchScheduleServiceImpl(NaverClientProperties naverClientProperties,
                                              NewsRepository newsRepository,
                                              NaverClient naverClient) {
        this.naverClientProperties = naverClientProperties;
        this.newsRepository = newsRepository;
        this.naverClient = naverClient;
    }

    @Override
    public V1NaverNewsResponse searchNaverNews(String regionName, String keyword) {
        return naverClient.getNewsList(naverClientProperties.getClientId(),
                                       naverClientProperties.getClientSecret(),
                                       regionName + " " + keyword);
    }

    @Override
    public void insertNaverNews(V1NaverNewsResponse v1NaverNewsResponse) {
        v1NaverNewsResponse.getItems().forEach(item -> {
            newsRepository.save(transform(item));
        });
    }

    private News transform(V1NaverNewsItems v1NaverNewsItems) {
        News news = new News();
        news.setTitle(removeHtmlTag(v1NaverNewsItems.getTitle()));
        news.setContent(removeHtmlTag(v1NaverNewsItems.getDescription()));
        news.setPath("Not yet");
        news.setWebUrl(v1NaverNewsItems.getOriginallink());
        news.setPubDate(v1NaverNewsItems.getPubDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return news;
    }

    private String removeHtmlTag(String original) {
        return original.replaceAll("<b>", "").replaceAll("</b>", "");
    }
}
