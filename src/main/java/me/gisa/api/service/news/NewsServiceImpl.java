package me.gisa.api.service.news;

import me.gisa.api.datatool.siseme.SisemeClient;
import me.gisa.api.datatool.siseme.model.Region;
import me.gisa.api.repository.NewsRepository;
import me.gisa.api.repository.entity.News;
import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.service.model.dto.NewsDto;
import me.gisa.api.service.model.NewsDtoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private static final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);

    private static final String NAVER_BASE_URL = "https://land.naver.com";
    private static final String DAUM_BASE_URL = "https://realestate.daum.net/news/region";
    private static final String REGION_TYPE = "sido";

    private final SisemeClient sisemeClient;
    private final NewsRepository newsRepository;
    private final ScrapService scrapService;

    public NewsServiceImpl(SisemeClient sisemeClient, NewsRepository newsRepository, ScrapService scrapService) {
        this.sisemeClient = sisemeClient;
        this.newsRepository = newsRepository;
        this.scrapService = scrapService;
    }

    @Override
    public void sync() throws IOException, JAXBException {

        List<Region> regionList = sisemeClient.getRegionList(REGION_TYPE).orElse(Collections.EMPTY_LIST);
        log.info("====================================[Crawl Service Start]====================================");
        for (Region region : regionList) {

            for (NewsType newsType : NewsType.values()) {

                if (newsType != NewsType.NAVER || newsType != NewsType.DAUM) { continue; }   // 주석 풀어야함.

                try {
                    Thread.sleep(3000);
                    log.info("[Region : {}, NewsType : {}] Crawling......", region.getCode(), newsType);
                    String baseUrl = newsType == NewsType.NAVER ? NAVER_BASE_URL + "/news/region.nhn" : DAUM_BASE_URL;
                    NewsDto newsDto = NewsDtoFactory.getNewsDto(baseUrl, newsType, region.getCode(), "1");
                    List<News> newsList = scrapService.getNewsList(newsDto);
                    int insertCount = saveNewsList(newsList);
                    log.info("[Crawl Count : {}, Insert Count : {}]", newsList.size(), insertCount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            break;
        }
        log.info("====================================[Crawl Service End]====================================");
    }

    private int saveNewsList(List<News> newsList) {
        List<News> originalNewsList = newsRepository.findAll();
        newsList = newsList.stream()
                           .filter(news -> !originalNewsList.stream()
                                                           .anyMatch(originalNews -> news.getOriginalLink()
                                                                                          .equals(originalNews.getOriginalLink())))
                           .peek(System.out::println)
                           .collect(Collectors.toList());
        return newsRepository.saveAll(newsList).size();
    }
}
