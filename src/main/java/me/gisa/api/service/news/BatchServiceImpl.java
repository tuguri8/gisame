package me.gisa.api.service.news;

import me.gisa.api.datatool.siseme.SisemeClient;
import me.gisa.api.datatool.siseme.model.Region;
import me.gisa.api.repository.NewsRepository;
import me.gisa.api.repository.entity.News;
import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.service.model.NewsDto;
import me.gisa.api.service.model.factory.NewsDtoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchServiceImpl implements BatchService {

    private static final Logger log = LoggerFactory.getLogger(BatchServiceImpl.class);

    private static final String NAVER_BASE_URL = "https://land.naver.com";
    private static final String DAUM_BASE_URL = "https://realestate.daum.net/news/region";
    private static final String REGION_TYPE = "sido";

    private final SisemeClient sisemeClient;
    private final NewsRepository newsRepository;
    private final NewsParserService scrapService;

    public BatchServiceImpl(SisemeClient sisemeClient, NewsRepository newsRepository, NewsParserService scrapService) {
        this.sisemeClient = sisemeClient;
        this.newsRepository = newsRepository;
        this.scrapService = scrapService;
    }

    @Override
    @Scheduled(cron = "* */5 * * * *")
    public void sync() throws IOException, JAXBException {
        long startTime = System.currentTimeMillis();
        List<Region> regionList = sisemeClient.getRegionList(REGION_TYPE).orElse(Collections.EMPTY_LIST);
        log.info("========[Crawl Service Start] : [ {} ]========", LocalDateTime.now());
        for (Region region : regionList) {

            for (NewsType newsType : NewsType.values()) {

                if (newsType == NewsType.GOOGLE || newsType == NewsType.UNKNOWN) { continue; }

                try {
                    Thread.sleep(3000);
                    log.info("[Region : {}, NewsType : {}] Crawling......", region.getCode(), newsType);
                    String baseUrl = newsType == NewsType.NAVER ? NAVER_BASE_URL + "/news/region.nhn" : DAUM_BASE_URL;
                    NewsDto newsDto = NewsDtoFactory.getNewsDto(baseUrl, newsType, region, "1");
                    List<News> newsList = scrapService.getNewsList(newsDto);
                    int insertCount = saveNewsList(newsList, newsType);
                    log.info("[Crawl Count : {}, Insert Count : {}]", newsList.size(), insertCount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("========[Crawl Service End] : [ {} sec ]========", (double) (endTime - startTime) / 1000);
    }

    private int saveNewsList(List<News> newsList, NewsType newsType) {
        List<News> originalNewsList = newsRepository.findAllByNewsType(newsType).orElse(Collections.EMPTY_LIST);
        newsList = newsList.stream()
                           .filter(news -> !originalNewsList.stream()
                                                            .anyMatch(originalNews -> news.getOriginalLink()
                                                                                          .equals(originalNews.getOriginalLink())))
                           .collect(Collectors.toList());
        return newsRepository.saveAll(newsList).size();
    }
}
