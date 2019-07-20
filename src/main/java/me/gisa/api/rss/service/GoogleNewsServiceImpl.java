package me.gisa.api.rss.service;

import me.gisa.api.datatool.siseme.SisemeClient;
import me.gisa.api.rss.repository.entity.NewsFromRss;
import me.gisa.api.rss.repository.NewsFromRssRepository;
import me.gisa.api.rss.service.model.Document;
import me.gisa.api.rss.service.model.Documents;
import me.gisa.api.rss.service.model.NewsFromRssModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleNewsServiceImpl implements GoogleNewsService {
    private final NewsFromRssRepository newsfromrssRepository;

    public GoogleNewsServiceImpl(NewsFromRssRepository newsfromrssRepository, SisemeClient sisemeClient) {
        this.newsfromrssRepository = newsfromrssRepository;
    }

    //google rss에서 가져옴
    public List<NewsFromRssModel> getNewsFromRss(String region) throws MalformedURLException, JAXBException, UnsupportedEncodingException {
        JAXBContext jaxbContext;
        jaxbContext = JAXBContext.newInstance(Documents.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        String koreanParameter = region + " 부동산";
        koreanParameter = URLEncoder.encode(koreanParameter, "UTF-8");
        String uri = "https://news.google.com/rss/search?q="
                + koreanParameter +
                "&hl=ko&gl=KR&ceid=KR:ko";
        URL newsuri = new URL(uri);
        Documents documents = (Documents) unmarshaller.unmarshal(newsuri);
        List<Document> newsList = documents.getDocumentList();
        return transform(newsList);

    }

    //가져온 뉴스기사들 DB에 저장
    public void saveNewsFromRssToDB(String region) throws MalformedURLException, JAXBException, UnsupportedEncodingException {
        List<NewsFromRssModel> newsFromRssModelList = getNewsFromRss(region);

        List<NewsFromRss> newsListFromDB = newsfromrssRepository.findAll();
        newsFromRssModelList.removeIf(duplicatedNews -> newsListFromDB.stream().anyMatch(newsFromDB -> duplicatedNews.getOriginalLink().equals(newsFromDB
                .getOriginalLink())));

        for (NewsFromRssModel newsfromRssModel : newsFromRssModelList) {
            NewsFromRss newsfromrss = new NewsFromRss();

            newsfromrss.setTitle(newsfromRssModel.getTitle());
            newsfromrss.setOriginalLink(newsfromRssModel.getOriginalLink());
            newsfromrss.setSummary(newsfromRssModel.getSummary());
            newsfromrss.setPubDate(newsfromRssModel.getPubDate());
            newsfromrss.setRegionName(region);

            newsfromrssRepository.save(newsfromrss);
        }
    }

    // 단위로 기사 데이터베이스 저장하기
    // @Scheduled(cron = "* */2 * * * *")
    public void SaveNewsfromRssScheduling() throws UnsupportedEncodingException, JAXBException, MalformedURLException {
        //sismeclient를 통해 전체 지역 리스트를 string으로 받아온다고 가정 ex) 경기도 광명시 소하동 , 서울시 영등포구 신길동...
        List<String> regionName = new ArrayList<String>(); //일단 지금은 값이 없는 상태

        for (String regionname : regionName) {
            saveNewsFromRssToDB(regionname);
        }
    }

    private List<NewsFromRssModel> transform(List<Document> documents) {
        List<NewsFromRssModel> newsFromRssModelList = new ArrayList<NewsFromRssModel>();
        for (Document document : documents) {
            NewsFromRssModel newsFromRssModel = new NewsFromRssModel();
            newsFromRssModel.setTitle(document.getTitle());
            LocalDate pubDate = LocalDate.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse(document.getPubDate()));
            newsFromRssModel.setPubDate(pubDate);
            newsFromRssModel.setOriginalLink(document.getOriginalLink());
            org.jsoup.nodes.Document summaryDoc = Jsoup.parse(document.getSummary());
            newsFromRssModel.setSummary(summaryDoc.getElementsByTag("p").get(0).text());

            newsFromRssModelList.add(newsFromRssModel);
        }
        return newsFromRssModelList;
    }

}