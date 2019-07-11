package me.gisa.api.naver.batch.service;

import me.gisa.api.datatool.naver.NaverClient;
import me.gisa.api.datatool.naver.model.v1.V1NaverNewsItems;
import me.gisa.api.datatool.naver.model.v1.V1NaverNewsKeyword;
import me.gisa.api.datatool.naver.model.v1.V1NaverNewsResponse;
import me.gisa.api.naver.repository.NewsRepository;
import me.gisa.api.naver.repository.entity.News;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Optional;

@Service
public class NaverNewsSearchScheduleServiceImpl implements NaverNewsSearchScheduleService {

    private final ModelMapper modelMapper;
    private final SisemeRegionSearchScheduleService sisemeRegionSearchScheduleService;
    private final NaverClient naverClient;
    private final NewsRepository newsRepository;
    private final ShortUrlService shortUrlService;

    public NaverNewsSearchScheduleServiceImpl(ModelMapper modelMapper,
                                              SisemeRegionSearchScheduleService sisemeRegionSearchScheduleService,
                                              NaverClient naverClient,
                                              NewsRepository newsRepository,
                                              ShortUrlService shortUrlService) {
        this.modelMapper = modelMapper;
        this.sisemeRegionSearchScheduleService = sisemeRegionSearchScheduleService;
        this.naverClient = naverClient;
        this.newsRepository = newsRepository;
        this.shortUrlService = shortUrlService;
    }

    @Override
    public Optional<V1NaverNewsResponse> getNewsList(String keyword) {
        Optional<V1NaverNewsResponse> optionalV1NaverNewsResponse =
            naverClient.getNewsList(keyword + " " + V1NaverNewsKeyword.getKeywordList());
        return optionalV1NaverNewsResponse;
    }

    @Override
    public void insertNaverNews(V1NaverNewsResponse v1NaverNewsResponse) {
        v1NaverNewsResponse.getItems().forEach(item -> {
            newsRepository.save(transform(item));
        });
    }

    private News transform(V1NaverNewsItems v1NaverNewsItems) {

        News news = new News();
        news.setTitle(v1NaverNewsItems.getTitle());
        news.setContent(v1NaverNewsItems.getDescription());
        news.setPath(shortUrlService.createShortUrl("",v1NaverNewsItems.getOriginallink()));
        news.setWebUrl(v1NaverNewsItems.getOriginallink());
        news.setPubDate(v1NaverNewsItems.getPubDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        news.setActive(Boolean.TRUE);
        return news;
    }

//    @Override
//    public V1NaverNewsResponse searchNaverNews(String regionName, String keyword) {
//        return naverClient.getNewsList(regionName + " " + keyword);
//    }
//
//    @Override
//    public void insertNaverNews(V1NaverNewsResponse v1NaverNewsResponse) {
//        v1NaverNewsResponse.getItems().forEach(item -> {
//            newsRepository.save(transform(item));
//        });
//    }




}
