package me.gisa.api.service.news.parser;

import me.gisa.api.repository.entity.News;
import me.gisa.api.service.model.NewsModel;
import me.gisa.api.service.model.dto.NewsDto;
import org.jsoup.nodes.Document;

import java.util.List;

public interface NewsParserService {

    List<News> getNewsList(Document document, NewsDto newsDto);
}
