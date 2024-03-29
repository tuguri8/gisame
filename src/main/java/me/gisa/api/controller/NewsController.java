package me.gisa.api.controller;

import me.gisa.api.service.gisame.GisameService;
import me.gisa.api.service.model.NewsModel;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/news")
public class NewsController {

    private final GisameService gisameService;

    public NewsController(GisameService gisameService) {
        this.gisameService = gisameService;
    }

    @GetMapping("/list")
    public List<NewsModel> getNewsList(@RequestParam("startDate") String startDate,
                                       @RequestParam("endDate") String endDate,
                                       @RequestParam("regionCode") String regionCode,
                                       @RequestParam("searchKeyword") String searchKeyword,
                                       @RequestParam("newsType") String newsType,
                                       Pageable pageable) {
        return gisameService.getNewsList(regionCode, searchKeyword, newsType, startDate, endDate, pageable);
    }
}
