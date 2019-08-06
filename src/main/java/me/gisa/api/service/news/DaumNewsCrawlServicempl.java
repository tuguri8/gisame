package me.gisa.api.service.news;

import me.gisa.api.repository.NewsRepository;
import me.gisa.api.repository.entity.News;
import me.gisa.api.repository.entity.NewsType;
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

@Service
public class DaumNewsCrawlServicempl implements NewsService {
    private static final Logger log = LoggerFactory.getLogger(DaumNewsCrawlServicempl.class);

    private final NewsRepository newsRepository;

    private final String URL_PREFIX = "https://realestate.daum.net";

    public DaumNewsCrawlServicempl(NewsRepository newsRepository) {this.newsRepository = newsRepository;}

    @Override
    @Scheduled(cron = "0 9 19 * * *")
    public void sync() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        // 기존 뉴스 리스트
        List<News> priorResult = newsRepository.findAllByNewsType(NewsType.DAUM).orElse(Collections.EMPTY_LIST);

        for (int i = 1; i < 11; i++) {
            String crawlURL = URL_PREFIX + "/news/region/seoul?page=" + i;
            Document doc = Jsoup.connect(crawlURL).execute().parse();

            // 뉴스 목록 받아오기
            Elements newsList = doc.select(".cont");

            // 뉴스 정보 받아오기
            for (Element e : newsList) {
                String newsURL = URL_PREFIX + e.getElementsByClass("link_txt").first().attr("href");

                if (priorResult.stream().anyMatch(priorNews -> isSameUrl(priorNews, newsURL))) continue;

                News news = new News();
                news.setTitle(e.getElementsByClass("tit").text());
                news.setSubLink(newsURL);
                news.setNewsType(NewsType.DAUM);
                news.setPubDate(LocalDate.parse(e.getElementsByClass("txt_date").text(), formatter));
                summarize(news);
            }
        }

    }

    private void summarize(News news) {
        try {
            Document doc = Jsoup.connect(news.getSubLink()).execute().parse();
            news.setContent(doc.select(".wrap_newsbody").text());
        } catch (IOException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        newsRepository.save(news);
        log.info("다음 뉴스 저장 완료");
    }

    private Boolean isSameUrl(News priorNews, String newsURL) {
        return priorNews.getSubLink().equals(newsURL);
    }
}
