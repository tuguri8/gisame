package me.gisa.api.service.news;

import com.google.common.collect.Lists;
import me.gisa.api.repository.entity.KeywordType;
import me.gisa.api.repository.entity.News;
import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.service.model.NewsDto;
import me.gisa.api.service.model.NewsModel;
import me.gisa.api.service.model.Tag;
import me.gisa.api.service.news.utils.ContentParser;
import me.gisa.api.service.news.utils.jsoup.JsoupBuilder;
import me.gisa.api.service.news.utils.summary.SummaryBuilder;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsParserServiceImpl implements NewsParserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsParserServiceImpl.class);

    @Override
    public List<News> getNewsList(NewsDto newsDto) {

        Optional<Document> optionalDocument = JsoupBuilder.getDocument(newsDto.getBaseUrl(), newsDto.getParameters());

        String baseUrl = newsDto.getNewsType() == NewsType.NAVER ? "https://land.naver.com" : "https://realestate.daum.net";
        newsDto.setBaseUrl(baseUrl);
        Optional<Elements> elements = getOptionalElements(optionalDocument, newsDto);
        List<NewsModel> newsModelList = getNewsModelList(elements, newsDto);
        List<News> newsList = getNewsList(newsModelList, newsDto);
        return newsList.stream().filter(this::isVaild).collect(Collectors.toList());
    }

    private List<NewsModel> getNewsModelList(Optional<Elements> elements, NewsDto newsDto) {

        if (!elements.isPresent()) { return Collections.EMPTY_LIST; }

        return elements.get().stream()
                       .map(element -> getNewsModel(element, newsDto))
                       .collect(Collectors.toList());
    }

    private List<News> getNewsList(List<NewsModel> newsModelList, NewsDto newsDto) {

        List<News> newsList = Lists.newArrayList();

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

    private Optional<Elements> getOptionalElements(Optional<Document> document, NewsDto newsDto) {

        if (!document.isPresent()) { return Optional.empty(); }
        Tag tag = newsDto.getTag();
        return Optional.of(document.get().select(tag.getNewsList()));
    }

    //뉴스 원본 URL과 썸네일 URL을 setting 한다.
    private NewsModel getNewsModel(Element element, NewsDto newsDto) {

        Tag tag = newsDto.getTag();
        NewsModel newsModel = new NewsModel();
        newsModel.setOriginalLink(newsDto.getBaseUrl() + element.select(tag.getPortalUrl()).attr("href"));
        newsModel.setThumbnail(element.select(tag.getThumbnail()).attr("src"));
        return newsModel;
    }

    //모든 attr을 setting한다.
    private News setAttributes(NewsModel newsModel, Document document, NewsDto newsDto) {

        SummaryBuilder summaryBuilder = new SummaryBuilder();
        Tag tag = newsDto.getTag();

        News news = new News();
        news.setTitle(document.select(tag.getTitle()).text().trim());
        news.setContent(ContentParser.parseContent(document.select(tag.getContent()).text().trim()));
        String pubDate = document.select(tag.getDate()).text().trim().split(" ")[0].replaceAll("\\.", "-");
        if (StringUtils.isEmpty(pubDate)) {
            news.setPubDate(LocalDate.now());
        } else {
            news.setPubDate(LocalDate.parse(pubDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        news.setPress(document.select(tag.getPress()).text().trim());

        news.setOriginalLink(newsModel.getOriginalLink());
        news.setThumbnail(newsModel.getThumbnail());
        news.setRegionCode(newsDto.getRegionCode());
        news.setSearchKeyword(KeywordType.BOODONGSAN);
        news.setNewsType(newsDto.getNewsType());
        news.setSummary(summaryBuilder.getSummary(news.getContent()));
        return news;
    }

    private boolean isVaild(News news) {
        return StringUtils.isNotEmpty(news.getTitle())
            && StringUtils.isNotEmpty(news.getContent())
            && StringUtils.isNotEmpty(news.getOriginalLink())
            && StringUtils.isNotEmpty(news.getSummary())
            && StringUtils.isNotEmpty(news.getPubDate().toString())
            && StringUtils.isNotEmpty(news.getPress());
    }
}
