package me.gisa.api.service.news;

import me.gisa.api.common.utils.NewsSummarizer;
import me.gisa.api.datatool.siseme.SisemeClient;
import me.gisa.api.datatool.siseme.model.Region;
import me.gisa.api.datatool.siseme.model.RegionGroup;
import me.gisa.api.datatool.siseme.model.RegionType;
import me.gisa.api.repository.NewsRepository;
import me.gisa.api.repository.entity.KeywordType;
import me.gisa.api.repository.entity.News;
import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.service.model.SisemeResultModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DaumNewsCrawlServicempl implements NewsService {
    private static final Logger log = LoggerFactory.getLogger(DaumNewsCrawlServicempl.class);

    private final NewsSummarizer newsSummarizer;
    private final NewsRepository newsRepository;
    private final SisemeClient sisemeClient;

    private final String URL_PREFIX = "https://realestate.daum.net";

    public DaumNewsCrawlServicempl(NewsSummarizer newsSummarizer,
                                   NewsRepository newsRepository,
                                   SisemeClient sisemeClient) {
        this.newsSummarizer = newsSummarizer;
        this.newsRepository = newsRepository;
        this.sisemeClient = sisemeClient;
    }

    // 시세미 API 검색 결과 리스트 반환
    private List getSisemeResult(RegionType regionType) {
        Optional<List<Region>> sisemeResult = sisemeClient.getRegionList(regionType.name());
        return sisemeResult.map(regions -> regions
            .stream()
            .map(this::transform)
            .collect(Collectors.toList())).orElse(Collections.EMPTY_LIST);
    }

    @Override
    @Scheduled(cron = "0 54 23 * * *")
    public void sync() throws IOException {

        List<SisemeResultModel> sisemeResultModelList = getSisemeResult(RegionType.SIDO);

        for (SisemeResultModel sisemeResultModel : sisemeResultModelList) {
            crawlDaumNews(sisemeResultModel);
        }
    }

    // 다음 부동산 뉴스 크롤링
    private void crawlDaumNews(SisemeResultModel sisemeResultModel) throws IOException {
        // 기존 뉴스 리스트
        List<News> priorResult = newsRepository.findAllByNewsType(NewsType.DAUM).orElse(Collections.EMPTY_LIST);
        Integer lastPage = sisemeResultModel.getCrawlKeyword().equals("seoul") ? 11 : 6; // 서울지역만 10페이지 까지 검색

        for (int i = 1; i < lastPage; i++) {
            String crawlURL = String.format("%s/news/region/%s?page=%d", URL_PREFIX, sisemeResultModel.getCrawlKeyword(), i);
            Document doc = Jsoup.connect(crawlURL).execute().parse();

            // 뉴스 목록 받아오기
            Elements newsList = doc.select(".cont");

            // 뉴스 정보 받아오기
            for (Element e : newsList) {
                String newsURL = URL_PREFIX + e.getElementsByClass("link_txt").first().attr("href");

                if (priorResult.stream().anyMatch(priorNews -> isSameUrl(priorNews, newsURL, sisemeResultModel.getCode()))) { continue; }

                News news = new News();
                news.setTitle(e.getElementsByClass("tit").text());
                news.setRegionCode(sisemeResultModel.getCode());
                news.setSearchKeyword(KeywordType.BOODONGSAN);
                news.setSubLink(newsURL);
                news.setNewsType(NewsType.DAUM);
                news.setPubDate(LocalDate.parse(e.getElementsByClass("txt_date").text(), DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
                newsSummarizer.summarizeNews(news);
            }
        }

    }

    private SisemeResultModel transform(Region region) {
        SisemeResultModel sisemeResultModel = new SisemeResultModel();
        sisemeResultModel.setType(region.getType());
        sisemeResultModel.setCode(region.getCode());
        sisemeResultModel.setCrawlKeyword(RegionGroup.findByRegionName(region.getFullName()).getCrawlKeyword());
        sisemeResultModel.setSearchKeyword(KeywordType.BOODONGSAN);
        return sisemeResultModel;
    }

    private Boolean isSameUrl(News priorNews, String newsURL, String code) {
        return priorNews.getSubLink().equals(newsURL) && priorNews.getRegionCode().equals(code);
    }
}
