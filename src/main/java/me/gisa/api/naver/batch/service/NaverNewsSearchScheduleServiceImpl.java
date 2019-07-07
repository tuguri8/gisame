package me.gisa.api.naver.batch.service;

import me.gisa.api.datatool.naver.NaverClient;
import me.gisa.api.datatool.naver.model.v1.V1NaverNewsItems;
import me.gisa.api.datatool.naver.model.v1.V1NaverNewsResponse;
import me.gisa.api.naver.repository.NewsRepository;
import me.gisa.api.naver.repository.entity.News;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@Service
@Configuration
public class NaverNewsSearchScheduleServiceImpl implements NaverNewsSearchScheduleService {

    private final NaverClient naverClient;
    private final NewsRepository newsRepository;

    public NaverNewsSearchScheduleServiceImpl(NaverClient naverClient, NewsRepository newsRepository) {
        this.naverClient = naverClient;
        this.newsRepository = newsRepository;
    }

    @Override
    public V1NaverNewsResponse searchNaverNews(String regionName, String keyword) {
        return naverClient.getNewsList(regionName + " " + keyword);
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
