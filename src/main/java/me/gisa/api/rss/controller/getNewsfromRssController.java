package me.gisa.api.rss.controller;

import me.gisa.api.datatool.rss.model.v1.V1RssNewsResponse;
import me.gisa.api.rss.repository.NewsfromrssRepository;
import me.gisa.api.rss.repository.entity.Newsfromrss;
import me.gisa.api.rss.service.GoogleNewsService;
import me.gisa.api.rss.service.model.NewsfromRssModel;
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
    private final GoogleNewsService GoogleNewsService;
    private final NewsfromrssRepository newsfromrssRepository;
    public getNewsfromRssController(GoogleNewsService GoogleNewsService, NewsfromrssRepository newsfromrssRepository){
        this.GoogleNewsService = GoogleNewsService;
        this.newsfromrssRepository=newsfromrssRepository;

    }

    //google rss에서 직접 뉴스기사 가져옴
    @GetMapping("api/rss/{region}")
    @ResponseBody
    public List<V1RssNewsResponse> getNewsFromRss (@PathVariable String region) throws URISyntaxException, MalformedURLException, JAXBException, UnsupportedEncodingException {
        List<V1RssNewsResponse> newsfromRssResponseList = new ArrayList<V1RssNewsResponse>();
        List<NewsfromRssModel> newsfromRssModelList = GoogleNewsService.getNewsfromRss(region);


        for(NewsfromRssModel newsfromRssModel : newsfromRssModelList){
            V1RssNewsResponse newsfromRssResponse=new V1RssNewsResponse(newsfromRssModel);
            newsfromRssResponseList.add(newsfromRssResponse);
        }
        return newsfromRssResponseList;
    }
    //google rss에서 뉴스기사를 가져온 뒤 db에 저장(test)
    @GetMapping("api/rss/save/{region}")
    @ResponseBody
    public List<Newsfromrss> saveNewsFromRsstoDB (@PathVariable String region) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException, JAXBException {
        List<V1RssNewsResponse> newsfromRssResponseList = new ArrayList<V1RssNewsResponse>();
        GoogleNewsService.saveNewsfromRssToDB(region);
        List<Newsfromrss> newsfromDB = newsfromrssRepository.findByRegionNameContaining((region));

        return newsfromDB;
    }
}