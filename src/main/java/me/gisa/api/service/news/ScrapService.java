package me.gisa.api.service.news;

import me.gisa.api.repository.entity.News;
import me.gisa.api.service.model.dto.NewsDto;

import java.util.List;

public interface ScrapService {

    List<News> getNewsList(NewsDto newsDto);

}
