package me.gisa.api.naver.batch.service;

import me.gisa.api.datatool.naver.model.v1.V1NaverNewsResponse;

import java.util.Optional;

public interface NaverNewsSearchScheduleService {

    Optional<V1NaverNewsResponse> getNewsList(String keyword);

    void insertNaverNews(V1NaverNewsResponse v1NaverNewsResponse);

//    V1NaverNewsResponse searchNaverNews(String regionName, String keyword);
//    void insertNaverNews(V1NaverNewsResponse v1NaverNewsResponse);

}



