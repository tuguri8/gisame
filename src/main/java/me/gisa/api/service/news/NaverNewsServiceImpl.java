package me.gisa.api.service.news;

import com.google.common.collect.Lists;
import me.gisa.api.datatool.naver.NaverClient;
import me.gisa.api.datatool.naver.model.v1.V1NaverNewsItems;
import me.gisa.api.datatool.naver.model.v1.V1NaverNewsResponse;
import me.gisa.api.datatool.siseme.SisemeClient;
import me.gisa.api.datatool.siseme.model.Region;
import me.gisa.api.datatool.siseme.model.RegionGroup;
import me.gisa.api.datatool.siseme.model.RegionType;
import me.gisa.api.repository.NewsRepository;
import me.gisa.api.repository.entity.KeywordType;
import me.gisa.api.repository.entity.News;
import me.gisa.api.repository.entity.NewsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NaverNewsServiceImpl implements NewsService {

    private static final Logger log = LoggerFactory.getLogger(NaverNewsServiceImpl.class);
    private static final Integer END_CALL = 100;

    private static final NewsType NEWS_TYPE = NewsType.NAVER;
    private static final KeywordType SEARCH_KEYWORD = KeywordType.BOODONGSAN;

    private final SisemeClient sisemeClient;
    private final NaverClient naverClient;
    private final NewsRepository newsRepository;

    public NaverNewsServiceImpl(SisemeClient sisemeClient, NaverClient naverClient, NewsRepository newsRepository) {
        this.sisemeClient = sisemeClient;
        this.naverClient = naverClient;
        this.newsRepository = newsRepository;
    }

    @Override
//    @Scheduled(cron = "* */5 * * * *")
    public void sync() {

        try {
            Thread.sleep(100);
            List<Region> regionList = getRegionList();
            List<News> originNewsList = newsRepository.findAllByNewsType(NEWS_TYPE).orElse(Collections.EMPTY_LIST);
            List<News> newsList = regionList.stream().map(this::getNewsList).flatMap(Collection::stream).collect(Collectors.toList());
            log.info("=====[DB 뉴스 갯수] : {}", originNewsList.size());
            log.info("=====[중복 제거 전 뉴스 갯수] : {}", newsList.size());
            newsList.removeIf(newNews -> originNewsList.stream().anyMatch(originNews -> isDuplicated(originNews, newNews)));
            log.info("=====[중복 제거 후 뉴스 갯수] : {}", newsList.size());
            newsRepository.saveAll(newsList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<News> getNewsList(Region region) {
        List<News> newsList = Lists.newArrayList();
        int start = 1;
        while (start++ <= END_CALL) {

            V1NaverNewsResponse v1NaverNewsResponse = naverClient.getNewsList(transform(region.getFullName()) + " " + transform(
            ), "100", start);

            List<News> tempNewsList = v1NaverNewsResponse.getItems().stream().map(item -> transform(item, region, SEARCH_KEYWORD))
                                                         .collect(Collectors.toList());
            newsList.addAll(tempNewsList);
            log.info("======[{}로 검색한 {}번째 페이지의 뉴스갯수 ] : {}",
                     region.getFullName() + " " + SEARCH_KEYWORD.getKeyword(),
                     start,
                     v1NaverNewsResponse.getItems().size());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return newsList;
    }

    private boolean isDuplicated(News originNews, News newNews) {
        return originNews.getOriginalLink().equals(newNews.getOriginalLink());
    }

    private List<Region> getRegionList() {
        Optional<List<Region>> optionalRegionList = sisemeClient.getRegionList(RegionType.SIDO.name());
        if (optionalRegionList.isPresent()) {
            List<Region> regionList = Lists.newArrayList();
            for (Region region : optionalRegionList.get()) {
                regionList.add(region);
            }
            return regionList;
        } else { return Collections.EMPTY_LIST; }
    }

    private News transform(V1NaverNewsItems v1NaverNewsItems, Region region, KeywordType keywordType) {

        News news = new News();
        news.setTitle(v1NaverNewsItems.getTitle());
        news.setContent(v1NaverNewsItems.getTitle());
        news.setRegionCode(region.getCode());
        news.setOriginalLink(Optional.ofNullable(v1NaverNewsItems.getOriginallink()).orElse(v1NaverNewsItems.getLink()));
        news.setSubLink(Optional.ofNullable(v1NaverNewsItems.getLink()).orElse(v1NaverNewsItems.getOriginallink()));
        news.setSearchKeyword(keywordType);
        news.setNewsType(NEWS_TYPE);
        news.setPubDate(v1NaverNewsItems.getPubDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return news;
    }

    private String transform() {
        return NaverNewsServiceImpl.SEARCH_KEYWORD.getKeyword();
    }

    private String transform(String regionGroup) {
        return RegionGroup.findByRegionName(regionGroup).getKeyword();
    }

}
