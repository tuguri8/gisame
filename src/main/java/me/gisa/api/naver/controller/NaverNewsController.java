package me.gisa.api.naver.controller;

import me.gisa.api.naver.service.model.NewsResponse;
import me.gisa.api.naver.service.NaverNewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NaverNewsController {

    private final NaverNewsService naverNewsService;

    public NaverNewsController(NaverNewsService naverNewsService) {
        this.naverNewsService = naverNewsService;
    }

    //DB에 저장된 뉴스 리스트 얻기
    @GetMapping("/list")
    public List<NewsResponse> getNewsList() {
        return naverNewsService.getNewsList();
    }

}
