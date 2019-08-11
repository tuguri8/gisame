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
import me.gisa.api.service.news.utils.jsoup.JsoupBuilder;
import me.gisa.api.service.news.utils.summary.Sentence;
import me.gisa.api.service.news.utils.summary.SummaryBuilder;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NaverNewsServiceImpl implements NewsService {

    private static final Logger log = LoggerFactory.getLogger(NaverNewsServiceImpl.class);
    private static final String BASE_URL = "https://land.naver.com/news/region.nhn";
    private static final String REGEX = "^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?" +
        "(#(\\w*))?$";

    private static final String REGION_TYPE = "sido";
    private static final NewsType NEWS_TYPE = NewsType.NAVER;
    private static final KeywordType SEARCH_KEYWORD = KeywordType.BOODONGSAN;

    private final SisemeClient sisemeClient;
    private final NewsRepository newsRepository;

    public NaverNewsServiceImpl(SisemeClient sisemeClient, NaverClient naverClient, NewsRepository newsRepository) {
        this.sisemeClient = sisemeClient;
        this.newsRepository = newsRepository;
    }

    @Override
    @Scheduled(cron = "* */10 * * * *")
    public void sync() {

        List<News> oldList = newsRepository.findAll();
        List<News> newsList = getNewsList();
        int count = 0;
        for (News news : newsList) {
            if (oldList.stream().anyMatch(i -> i.getOriginalLink().equals(news.getOriginalLink()))) { continue; }
            newsRepository.save(news);
            count += 1;
        }
        log.info("DB success : {} ", count);
    }

    private List<News> getNewsList() {

        List<Region> regionList = sisemeClient.getRegionList(REGION_TYPE).orElse(Collections.EMPTY_LIST);
        List<News> newsDtoList = new ArrayList<>();

        for (Region region : regionList) {

            try {
                Thread.sleep(5000);
                Map<String, String> parameters = JsoupBuilder.getParamMap(region.getCode(), "1");
                Optional<Document> document = JsoupBuilder.getDocument(BASE_URL, parameters);
                Elements html = document.get().select(".headline_list li");

                List<News> newsList = html.stream()
                                          .map(child -> transform(child, region.getCode()))
                                          .filter(news -> {
                                              String link = news.getOriginalLink();
                                              return StringUtils.isNotEmpty(link) && link.matches(REGEX);
                                          })
                                          .filter(news -> {
                                              try {
                                                  Thread.sleep(1000);
                                                  news.setContent(getNewsContent(news));
                                                  news.setSummary(getSummary(news.getContent()));

                                              } catch (InterruptedException e) {

                                              }
                                              return isValidNews(news);
                                          })
                                          .collect(Collectors.toList());
                newsDtoList.addAll(newsList);
                log.info("Region Code : {} , Success Crawled : {} ", region.getCode(), newsList.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return newsDtoList;
    }

    private boolean isValidNews(News news) {
        return StringUtils.isNotEmpty(news.getRegionCode())
            && StringUtils.isNotEmpty(news.getContent())
            && StringUtils.isNotEmpty(news.getOriginalLink())
            && StringUtils.isNotEmpty(news.getSummary())
            && StringUtils.isNotEmpty(news.getSubLink())
            && StringUtils.isNotEmpty(news.getTitle())
            && StringUtils.isNotEmpty(news.getPress())
            && !Objects.isNull(news.getPubDate());
    }

    private String getNewsContent(News news) {

        Optional<Document> document = JsoupBuilder.getDocument(news.getOriginalLink());
        return document.get().select("#articleBody").text();
    }

    private String getSummary(String text) {
        SummaryBuilder summaryBuilder = new SummaryBuilder();
        return summaryBuilder.getSummary(text).stream().limit(3).map(Sentence::getValue).collect(Collectors.joining(""));
    }

    private News transform(Element child, String code) {
        News news = new News();
        SummaryBuilder summaryBuilder = new SummaryBuilder();
        String title = child.select("dt a").text();
//        String summary = child.select("dd").text();
        String link = child.select(".photo a").attr("abs:href");
        String writing = child.select(".writing").text();
        String thumnail = child.select(".photo a img").attr("src");
        LocalDate date = LocalDate.parse(child.select(".date").text().replaceAll("\\.", "-"), DateTimeFormatter.ISO_LOCAL_DATE);
        news.setTitle(title);
        news.setOriginalLink(link);
        news.setSubLink(thumnail);
        news.setPress(writing);
        news.setSubLink(thumnail);
        news.setPubDate(date);
        news.setRegionCode(code);
        news.setNewsType(NewsType.NAVER);
        news.setSearchKeyword(KeywordType.BOODONGSAN);
        return news;
    }

}
