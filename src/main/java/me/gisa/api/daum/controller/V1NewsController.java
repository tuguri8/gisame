package me.gisa.api.daum.controller;

import me.gisa.api.datatool.siseme.model.RegionType;
import me.gisa.api.daum.service.NewsService;
import me.gisa.api.daum.service.model.DaumResultmodel;
import me.gisa.api.daum.service.model.SisemeResultModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class V1NewsController {

    private final NewsService newsService;

    public V1NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    //siseme API를 통한 지역 검색
    @GetMapping("/siseme/{regionName}")
    public List<SisemeResultModel> getSisemeResult(@PathVariable String regionName) {
        return newsService.getSisemeResult(RegionType.fromString(regionName));
    }

    //kakao API를 통한 뉴스 검색
    @GetMapping("/daum/{query}")
    public List<DaumResultmodel> getDaumResult(@PathVariable String query) {
        return newsService.getDaumResult(query);
    }

    //siseme APi를 통한 지역에 따른 뉴스 검색
    @GetMapping("/batch")
    public void getNewsBySiseme() {
        newsService.getNewsBySiseme();
    }
}
