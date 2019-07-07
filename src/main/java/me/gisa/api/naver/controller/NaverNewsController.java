package me.gisa.api.naver.controller;

import me.gisa.api.naver.controller.model.PageVO;
import me.gisa.api.naver.service.model.NewsResponse;
import me.gisa.api.naver.service.NaverNewsService;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/list")
    @Cacheable()
    public List<NewsResponse> getNewsList(PageVO page) {
        Pageable pageable = page.makePageable(0, "id");
        return naverNewsService.getNewsList(pageable);
    }
}
