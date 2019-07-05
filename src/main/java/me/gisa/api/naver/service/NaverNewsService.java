package me.gisa.api.naver.service;

import java.util.List;

import me.gisa.api.naver.repository.entity.NaverNews;

public interface NaverNewsService {

    List<NaverNews> getNewsList();

    void syncNewsList();

    //굳이 Service 인터페이스에 선언할 필요가 없을것 같다.
    //서비스 구현체에서 private로 선언.
    //Optional<List<Region>> getRegionList(String regionType);

}
