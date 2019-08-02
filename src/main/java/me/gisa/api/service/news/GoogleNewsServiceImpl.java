package me.gisa.api.service.news;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.collections4.ListUtils;
import org.jsoup.Jsoup;
import org.springframework.scheduling.annotation.Scheduled;

import me.gisa.api.datatool.rss.model.v1.Document;
import me.gisa.api.datatool.rss.model.v1.Documents;
import me.gisa.api.datatool.siseme.SisemeClient;
import me.gisa.api.datatool.siseme.model.Region;
import me.gisa.api.datatool.siseme.model.RegionGroup;
import me.gisa.api.datatool.siseme.model.RegionType;
import me.gisa.api.repository.NewsRepository;
import me.gisa.api.repository.entity.KeywordType;
import me.gisa.api.repository.entity.News;
import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.service.model.NewsModel;
import me.gisa.api.service.model.SisemeResultModel;

public class GoogleNewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final SisemeClient sismecClient;

    public GoogleNewsServiceImpl(NewsRepository newsRepository, SisemeClient sisemeClient) {
        this.newsRepository = newsRepository;
        this.sismecClient = sisemeClient;
    }

    private List getSisemeResult(RegionType regionType) {
        Optional<List<Region>> sisemeResult = sismecClient.getRegionList(regionType.name());

        return sisemeResult.map(regions -> regions.stream()
                .map(this::transform)
                .collect(Collectors.toList())).orElse(Collections.EMPTY_LIST);
    }

    //google rss에서 가져옴
    public List<NewsModel> getNewsFromRss(SisemeResultModel sisemeResultModel) throws MalformedURLException, JAXBException, UnsupportedEncodingException {
        JAXBContext jaxbContext;
        jaxbContext = JAXBContext.newInstance(Documents.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        String koreanParameter = sisemeResultModel.getKeyword();
        koreanParameter = URLEncoder.encode(koreanParameter, "UTF-8");
        String uri = "https://news.google.com/rss/search?q="
                + koreanParameter +
                "&hl=ko&gl=KR&ceid=KR:ko";
        URL newsuri = new URL(uri);
        Documents documents = (Documents) unmarshaller.unmarshal(newsuri);
        List<Document> newsList = documents.getDocumentList();
        return transform(newsList, sisemeResultModel);
    }

    public List<NewsModel> getNewsFromRss(String keyword) throws MalformedURLException, JAXBException, UnsupportedEncodingException {
        JAXBContext jaxbContext;
        jaxbContext = JAXBContext.newInstance(Documents.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        String koreanParameter = keyword;
        koreanParameter = URLEncoder.encode(koreanParameter, "UTF-8");
        String uri = "https://news.google.com/rss/search?q="
                + koreanParameter +
                "&hl=ko&gl=KR&ceid=KR:ko";
        URL newsuri = new URL(uri);
        Documents documents = (Documents) unmarshaller.unmarshal(newsuri);
        List<Document> newsList = documents.getDocumentList();
        return transform(newsList);
    }

    //DB 저장

    @Override
    @Scheduled(cron = "* */2 * * * *")
    public void sync() throws UnsupportedEncodingException, JAXBException, MalformedURLException {
        List<SisemeResultModel> sisemeResultModelList = ListUtils.union(getSisemeResult(RegionType.SIDO), getSisemeResult(RegionType.GUNGU));

        List<NewsModel> newsFromRssModelList = new ArrayList<>();
        for (SisemeResultModel sisemeResultModel : sisemeResultModelList) {
            List<NewsModel> newsFromRss = getNewsFromRss(sisemeResultModel);
            newsFromRssModelList.addAll(newsFromRss);
        }

        List<News> newsListFromDB = newsRepository.findAllByNewsType(NewsType.GOOGLE).orElse(Collections.EMPTY_LIST);
        newsFromRssModelList.removeIf(duplicatedNews -> newsListFromDB.stream()
                .anyMatch(newsFromDB -> duplicatedNews.getOriginalLink().equals(newsFromDB
                        .getOriginalLink())));

        for (NewsModel newsModel : newsFromRssModelList) {
            News newNews = new News();

            newNews.setTitle(newsModel.getTitle());
            newNews.setOriginalLink(newsModel.getOriginalLink());
            newNews.setSummary(newsModel.getSummary());
            newNews.setPubDate(newsModel.getPubDate());
            newNews.setNewsType(NewsType.GOOGLE);
            newNews.setRegionCode(newsModel.getRegionCode());
            newNews.setSearchKeyword(newsModel.getSearchKeyword());

            newsRepository.save(newNews);
        }
    }

    private SisemeResultModel transform(Region region) {
        String keyword;
        keyword = RegionGroup.findByRegionName(region.getFullName()).getKeyword() + " " +
                KeywordType.BOODONGSAN.getKeyword();

        SisemeResultModel sisemeResultModel = new SisemeResultModel();
        sisemeResultModel.setType(region.getType());
        sisemeResultModel.setCode(region.getCode());
        sisemeResultModel.setKeyword(keyword);

        sisemeResultModel.setSearchKeyword(KeywordType.BOODONGSAN);

        return sisemeResultModel;
    }

    private List<NewsModel> transform(List<Document> documents, SisemeResultModel sisemeResultModel) {
        List<NewsModel> newsModelList = new ArrayList<NewsModel>();
        for (Document document : documents) {
            NewsModel newsModel = new NewsModel();

            newsModel.setTitle(document.getTitle());
            LocalDate pubDate = LocalDate.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse(document.getPubDate()));
            newsModel.setPubDate(pubDate);
            newsModel.setOriginalLink(document.getOriginalLink());
            org.jsoup.nodes.Document summaryDoc = Jsoup.parse(document.getSummary());
            newsModel.setSummary(summaryDoc.getElementsByTag("p").get(0).text());
            newsModel.setNewsType(NewsType.GOOGLE);
            newsModel.setRegionCode(sisemeResultModel.getCode());
            newsModel.setSearchKeyword(sisemeResultModel.getSearchKeyword());

            newsModelList.add(newsModel);
        }
        return newsModelList;
    }

    private List<NewsModel> transform(List<Document> documents) {
        List<NewsModel> newsModelList = new ArrayList<NewsModel>();
        for (Document document : documents) {
            NewsModel newsModel = new NewsModel();

            newsModel.setTitle(document.getTitle());
            LocalDate pubDate = LocalDate.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse(document.getPubDate()));
            newsModel.setPubDate(pubDate);
            newsModel.setOriginalLink(document.getOriginalLink());
            org.jsoup.nodes.Document summaryDoc = Jsoup.parse(document.getSummary());
            newsModel.setSummary(summaryDoc.getElementsByTag("p").get(0).text());
            newsModel.setNewsType(NewsType.GOOGLE);

            newsModelList.add(newsModel);
        }
        return newsModelList;
    }
}
