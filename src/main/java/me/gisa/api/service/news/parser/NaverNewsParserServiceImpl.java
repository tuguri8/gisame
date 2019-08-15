package me.gisa.api.service.news.parser;

import me.gisa.api.repository.entity.KeywordType;
import me.gisa.api.repository.entity.News;
import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.service.model.NewsModel;
import me.gisa.api.service.model.dto.NewsDto;
import me.gisa.api.service.model.Tag;
import me.gisa.api.service.news.utils.ContentParser;
import me.gisa.api.service.news.utils.jsoup.JsoupBuilder;
import me.gisa.api.service.news.utils.summary.Sentence;
import me.gisa.api.service.news.utils.summary.SummaryBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NaverNewsParserServiceImpl implements NewsParserService {

    @Override
    public List<News> getNewsList(Document document, NewsDto newsDto) {

        String NAVER_BASE_URL = "https://land.naver.com";
        newsDto.setBaseUrl(NAVER_BASE_URL);
        Tag tag = newsDto.getTag();
        Elements elements = document.select(tag.getNewsListTag()).select(tag.getMultiLineUrlTag()).not(".photo");

        List<NewsModel> newsModelList = elements.stream()
                                                .map(element -> transform(element, newsDto))
                                                .collect(Collectors.toList());

        List<News> newsList = new ArrayList<>();

        for (NewsModel newsModel : newsModelList) {
            try {
                Thread.sleep(3000);
                Optional<Document> singleNewsDocument = JsoupBuilder.getDocument(newsModel.getOriginalLink());
                if (singleNewsDocument.isPresent()) {
                    newsList.add(setAttributes(newsModel, singleNewsDocument.get(), tag));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return newsList;
    }

    private News setAttributes(NewsModel newsModel, Document document, Tag tag) {
        News news = new News();
        SummaryBuilder summaryBuilder = new SummaryBuilder();
        news.setPress(document.select(tag.getPressTag()).text().trim());
        news.setPubDate(LocalDate.parse(document.select(tag.getDateTag()).text().trim().split(" ")[0].replaceAll("\\.", "-"),
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        news.setTitle(newsModel.getTitle());
        news.setNewsType(NewsType.NAVER);
        news.setSearchKeyword(KeywordType.BOODONGSAN);
        news.setOriginalLink(newsModel.getOriginalLink());
        news.setSubLink(document.select(tag.getThumnailTag()).attr("src").trim());
        news.setContent(ContentParser.parseContent(document.select(tag.getContentTag()).text().trim()));
        news.setSummary(summaryBuilder.getSummary(news.getContent()));
        news.setRegionCode(newsModel.getRegionCode());
        return news;
    }

    private NewsModel transform(Element element, NewsDto newsDto) {

        Tag tag = newsDto.getTag();

        String url = element.select(tag.getSingleUrlTag()).attr("href");
        String title = element.select(tag.getSingleUrlTag()).text();

        NewsModel newsModel = new NewsModel();
        newsModel.setTitle(title);
        newsModel.setRegionCode(newsDto.getRegionCode());
        newsModel.setOriginalLink(newsDto.getBaseUrl() + url);
        return newsModel;
    }

}
