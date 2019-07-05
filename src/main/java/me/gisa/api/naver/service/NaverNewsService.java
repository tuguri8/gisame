package me.gisa.api.naver.service;

import java.util.List;

import me.gisa.api.naver.service.model.NewsResponse;

public interface NaverNewsService {

    List<NewsResponse> getNewsList();

}
