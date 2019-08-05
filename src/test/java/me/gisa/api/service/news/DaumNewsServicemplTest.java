package me.gisa.api.service.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class DaumNewsServicemplTest {

    private static final Logger log = LoggerFactory.getLogger(DaumNewsServicemplTest.class);


    @Test
    public void 크롤링_테스트() throws IOException {
        String URL = "https://realestate.daum.net/news/region/seoul?page=1";
        Document doc = Jsoup.connect(URL).get();
        Elements elem = doc.select(".cont");
        for(Element e: elem) {
            log.info(e.getElementsByClass("tit").text());
            log.info(e.getElementsByClass("link_txt").first().attr("href"));
            log.info(e.getElementsByClass("txt_date").text());
        }
    }

    @Test
    public void 날짜_변환_테스트() {
        String test=  "2019.08.05 04:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        LocalDate localDate = LocalDate.parse(test, formatter);
        log.info(localDate.toString());
    }

    @Test
    public void 내용_크롤링() throws IOException {
        String URL = "https://realestate.daum.net/news/detail/region/seoul/20190804172503840";
        Document doc = Jsoup.connect(URL).get();
        String content = doc.select(".wrap_newsbody").text();
        log.info(content);
    }

}