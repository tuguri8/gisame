package me.gisa.api.rss.controller;

import me.gisa.api.datatool.rss.model.NewsfromRssResponse;
import me.gisa.api.rss.repository.NewsfromrssRepository;
import me.gisa.api.rss.repository.entity.Newsfromrss;
import me.gisa.api.rss.service.getNewsfromRssService;
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
    private final me.gisa.api.rss.service.getNewsfromRssService getNewsfromRssService;
    private final NewsfromrssRepository newsfromrssRepository;
    public getNewsfromRssController(getNewsfromRssService getNewsfromRssService,NewsfromrssRepository newsfromrssRepository){
        this.getNewsfromRssService=getNewsfromRssService;
        this.newsfromrssRepository=newsfromrssRepository;

    }

    //google rss에서 직접 뉴스기사 가져옴
    @GetMapping("api/rss/{region}")
    @ResponseBody
    public List<NewsfromRssResponse> getNewsFromRss (@PathVariable String region) throws URISyntaxException, MalformedURLException, JAXBException, UnsupportedEncodingException {
        List<NewsfromRssResponse> newsfromRssResponseList = new ArrayList<NewsfromRssResponse>();
        List<NewsfromRssModel> newsfromRssModelList = getNewsfromRssService.getNewsfromRss(region);


        for(NewsfromRssModel newsfromRssModel : newsfromRssModelList){
            NewsfromRssResponse newsfromRssResponse=new NewsfromRssResponse(newsfromRssModel);
            newsfromRssResponseList.add(newsfromRssResponse);
        }
        return newsfromRssResponseList;
    }
    //google rss에서 뉴스기사를 가져온 뒤 db에 저장(test)
    @GetMapping("api/rss/save/{region}")
    @ResponseBody
    public List<Newsfromrss> saveNewsFromRsstoDB (@PathVariable String region) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException, JAXBException {
        List<NewsfromRssResponse> newsfromRssResponseList = new ArrayList<NewsfromRssResponse>();
        getNewsfromRssService.saveNewsfromRssToDB(region);
        List<Newsfromrss> newsfromDB = newsfromrssRepository.findByRegionNameContaining((region));

        return newsfromDB;
    }
}