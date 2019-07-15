package me.gisa.api.service.gisame;

import me.gisa.api.service.model.NewsModel;

import java.util.List;

public interface GisameService {
    List<NewsModel> getNewsList();
}
