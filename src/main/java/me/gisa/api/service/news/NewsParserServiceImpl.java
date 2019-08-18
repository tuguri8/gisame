package me.gisa.api.service.news;

import me.gisa.api.repository.entity.KeywordType;
import me.gisa.api.repository.entity.News;
import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.service.model.NewsModel;
import me.gisa.api.service.model.NewsDto;
import me.gisa.api.service.model.Tag;
import me.gisa.api.service.news.utils.ContentParser;
import me.gisa.api.service.news.utils.jsoup.JsoupBuilder;
import me.gisa.api.service.news.utils.summary.SummaryBuilder;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsParserServiceImpl implements NewsParserService {

    @Override
    public List<News> getNewsList(Document document, NewsDto newsDto) {

        String baseUrl = newsDto.getNewsType() == NewsType.NAVER ? "https://land.naver.com" : "https://realestate.daum.net";
        newsDto.setBaseUrl(baseUrl);

        Optional<Elements> elements = getNewsListElements(document, newsDto);
        List<NewsModel> newsModelList = getNewsModelList(elements, newsDto);
        List<News> newsList = getNewsList(newsModelList, newsDto);
        return newsList;
    }

    private List<NewsModel> getNewsModelList(Optional<Elements> elements, NewsDto newsDto) {
        List<NewsModel> newsModelList = new ArrayList<>();
        if (elements.isPresent()) {
            newsModelList = elements.get().stream()
                                    .map(element -> getNewsModel(element, newsDto))
                                    .collect(Collectors.toList());
        }
        return newsModelList;
    }

    private List<News> getNewsList(List<NewsModel> newsModelList, NewsDto newsDto) {

        List<News> newsList = new ArrayList<>();

        for (NewsModel newsModel : newsModelList) {
            try {
                Thread.sleep(1500);
                Optional<Document> singleNewsDocument = JsoupBuilder.getDocument(newsModel.getOriginalLink());
                if (singleNewsDocument.isPresent()) {
                    newsList.add(setAttributes(newsModel, singleNewsDocument.get(), newsDto));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return newsList;
    }

    private Optional<Elements> getNewsListElements(Document document, NewsDto newsDto) {
        Tag tag = newsDto.getTag();
        Optional<Elements> elements = Optional.empty();
        switch (newsDto.getNewsType()) {
            case NAVER:
                elements = Optional.of(document.select(tag.getNewsListTag())/*.select(tag.getMultiLineUrlTag() + " > dt")*/);
                break;
            case DAUM:
                elements = Optional.of(document.select(tag.getNewsListTag()));
                break;
        }
        return elements;
    }

    //뉴스 원본 URL과 썸네일 URL을 setting 한다.
    private NewsModel getNewsModel(Element element, NewsDto newsDto) {

        Tag tag = newsDto.getTag();
        String thumbnail = element.select(tag.getThumnailTag()).attr("src");
        String url = element.select(tag.getSingleUrlTag()).attr("href");
        NewsModel newsModel = new NewsModel();
        newsModel.setOriginalLink(newsDto.getBaseUrl() + url);
        newsModel.setThumbnail(thumbnail);
        return newsModel;
    }

    //모든 attr을 setting한다.
    private News setAttributes(NewsModel newsModel, Document document, NewsDto newsDto) {

        SummaryBuilder summaryBuilder = new SummaryBuilder();
        Tag tag = newsDto.getTag();

        News news = new News();
        news.setTitle(document.select(tag.getTitleTag()).text().trim());
        news.setContent(ContentParser.parseContent(document.select(tag.getContentTag()).text().trim()));
        String pubDate = document.select(tag.getDateTag()).text().trim().split(" ")[0].replaceAll("\\.", "-");
        if (StringUtils.isEmpty(pubDate)) {
            news.setPubDate(LocalDate.now());
        } else {
            news.setPubDate(LocalDate.parse(pubDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        news.setPress(document.select(tag.getPressTag()).text().trim());

        news.setOriginalLink(newsModel.getOriginalLink());
        news.setThumbnail(newsModel.getThumbnail());
        news.setRegionCode(newsDto.getRegionCode());
        news.setSearchKeyword(KeywordType.BOODONGSAN);
        news.setNewsType(newsDto.getNewsType());

        news.setSummary(summaryBuilder.getSummary(news.getContent()));

        return news;
    }

}
