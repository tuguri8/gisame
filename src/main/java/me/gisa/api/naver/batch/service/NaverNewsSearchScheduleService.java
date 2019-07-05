package me.gisa.api.naver.batch.service;

import me.gisa.api.datatool.naver.model.v1.V1NaverNewsResponse;
import me.gisa.api.naver.repository.entity.News;

import java.util.List;

public interface NaverNewsSearchScheduleService {

    //네이버 뉴스 검색 객체 반환
    //[지역이름 + 부동산/전세/월세] 로 검색
    V1NaverNewsResponse searchNaverNews(String regionName, String keyword);

    //네이버 뉴스 DB에 저장
    void insertNaverNews(V1NaverNewsResponse v1NaverNewsResponse);
}
