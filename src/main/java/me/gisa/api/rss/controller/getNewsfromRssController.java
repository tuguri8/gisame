package me.gisa.api.rss.controller;

import me.gisa.api.datatool.rss.model.v1.V1RssNewsResponse;
import me.gisa.api.service.model.NewsModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class getNewsfromRssController {
    private final GoogleNewsService googleNewsService;

    public getNewsfromRssController(GoogleNewsService GoogleNewsService) {
        this.googleNewsService = GoogleNewsService;
    }

    //google rss에서 직접 뉴스기사 가져옴
    @GetMapping("api/rss/{region}")
    @ResponseBody
    public List<V1RssNewsResponse> getNewsFromRss(@PathVariable String region) throws URISyntaxException, MalformedURLException, JAXBException, UnsupportedEncodingException {
        List<V1RssNewsResponse> newsFromRssResponseList = new ArrayList<V1RssNewsResponse>();
        List<NewsModel> newsFromRssModelList = googleNewsService.getNewsFromRss(region);


        for (NewsModel newsModel : newsFromRssModelList) {
            V1RssNewsResponse newsFromRssResponse = new V1RssNewsResponse(newsModel);
            newsFromRssResponseList.add(newsFromRssResponse);
        }
        return newsFromRssResponseList;
    }

}