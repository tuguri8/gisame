package me.gisa.api.rss.controller;

import me.gisa.api.datatool.rss.model.v1.V1RssNewsResponse;
import me.gisa.api.rss.repository.NewsFromRssRepository;
import me.gisa.api.rss.repository.entity.NewsFromRss;
import me.gisa.api.rss.service.GoogleNewsService;
import me.gisa.api.rss.service.model.NewsFromRssModel;
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
    private final NewsFromRssRepository newsFromRssRepository;

    public getNewsfromRssController(GoogleNewsService GoogleNewsService, NewsFromRssRepository newsFromRssRepository) {
        this.googleNewsService = GoogleNewsService;
        this.newsFromRssRepository = newsFromRssRepository;

    }

    //google rss에서 직접 뉴스기사 가져옴
    @GetMapping("api/rss/{region}")
    @ResponseBody
    public List<V1RssNewsResponse> getNewsFromRss(@PathVariable String region) throws URISyntaxException, MalformedURLException, JAXBException, UnsupportedEncodingException {
        List<V1RssNewsResponse> newsFromRssResponseList = new ArrayList<V1RssNewsResponse>();
        List<NewsFromRssModel> newsFromRssModelList = googleNewsService.getNewsFromRss(region);


        for (NewsFromRssModel newsFromRssModel : newsFromRssModelList) {
            V1RssNewsResponse newsFromRssResponse = new V1RssNewsResponse(newsFromRssModel);
            newsFromRssResponseList.add(newsFromRssResponse);
        }
        return newsFromRssResponseList;
    }

    //google rss에서 뉴스기사를 가져온 뒤 db에 저장(test)
    @GetMapping("api/rss/save/{region}")
    @ResponseBody
    public List<NewsFromRss> saveNewsFromRssToDB(@PathVariable String region) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException, JAXBException {
        List<V1RssNewsResponse> newsFromRssResponseList = new ArrayList<V1RssNewsResponse>();
        googleNewsService.saveNewsFromRssToDB(region);
        List<NewsFromRss> newsFromDB = newsFromRssRepository.findByRegionNameContaining((region));

        return newsFromDB;
    }
}