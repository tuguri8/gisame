package me.gisa.api.service.news;

import me.gisa.api.repository.entity.News;
import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.service.model.dto.NewsDto;
import me.gisa.api.service.news.parser.DaumNewsParserServiceImpl;
import me.gisa.api.service.news.parser.NaverNewsParserServiceImpl;
import me.gisa.api.service.news.parser.NewsParserService;
import me.gisa.api.service.news.utils.jsoup.JsoupBuilder;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScrapServiceImpl implements ScrapService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrapServiceImpl.class);

    @Override
    public List<News> getNewsList(NewsDto newsDto) {

        List<News> newsList = new ArrayList<>();
        NewsParserService newsParser = newsDto.getNewsType() == NewsType.NAVER ? new NaverNewsParserServiceImpl() : new DaumNewsParserServiceImpl();
        Optional<Document> optionalDocument = JsoupBuilder.getDocument(newsDto.getBaseUrl(), newsDto.getParameters());
        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            newsList = newsParser.getNewsList(document, newsDto);
        }

        return newsList;
    }
}
