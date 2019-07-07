package me.gisa.api.daum.controller.v1;

import me.gisa.api.daum.controller.v1.dto.BatchResponse;
import me.gisa.api.daum.service.NewsService;
import me.gisa.api.daum.service.model.DaumResultmodel;
import me.gisa.api.daum.service.model.NewsBySisemeModel;
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

    @GetMapping("/siseme/{regionName}")
    public List<SisemeResultModel> getSisemeResult(@PathVariable String regionName) {
        return newsService.getSisemeResult(regionName);
    }

    @GetMapping("/daum/{query}")
    public List<DaumResultmodel> getDaumResult(@PathVariable String query) {
        return newsService.getDaumResult(query);
    }

    @GetMapping("/batch")
    public List<NewsBySisemeModel> getNewsBySiseme() {
        System.out.println("실행은됨");
        return newsService.getNewsBySiseme();
    }
}
