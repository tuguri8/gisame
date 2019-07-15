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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class NaverNewsServiceImpl implements NewsService {

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
    @Scheduled(cron = "* */2 * * * *")
    public void sync() {
        List<Region> regionList = getRegionList();
        for (Region region : regionList) {
            Optional<V1NaverNewsResponse> v1NaverNewsResponse = naverClient.getNewsList(transform(region.getFullName()) + " " + transform(
                SEARCH_KEYWORD));

            if (v1NaverNewsResponse.isPresent()) {
                for (V1NaverNewsItems item : v1NaverNewsResponse.get().getItems()) {
                    try {
                        Thread.sleep(100);
                        newsRepository.save(transform(item, region, SEARCH_KEYWORD));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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
        news.setOriginalLink(v1NaverNewsItems.getOriginallink());
        news.setSubLink(v1NaverNewsItems.getLink());
        news.setSearchKeyword(keywordType);
        news.setNewsType(NEWS_TYPE);
        news.setPubDate(v1NaverNewsItems.getPubDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return news;
    }

    private String transform(KeywordType keywordType) {
        return keywordType.getKeyword();
    }

    private String transform(String regionGroup) {
        return RegionGroup.findByRegionName(regionGroup).getKeyword();
    }

}
