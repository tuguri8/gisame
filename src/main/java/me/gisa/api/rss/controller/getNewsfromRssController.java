package me.gisa.api.rss.controller;

import me.gisa.api.datatool.rss.model.NewsfromRssResponse;
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

    public getNewsfromRssController(getNewsfromRssService getNewsfromRssService){
        this.getNewsfromRssService=getNewsfromRssService;
    }

    @GetMapping("api/rss/{region}")
    @ResponseBody
    public List<NewsfromRssResponse> getNewsFromRss (@PathVariable String region) throws URISyntaxException, MalformedURLException, JAXBException, UnsupportedEncodingException {
        List<NewsfromRssResponse> newsfromRssResponseList = new ArrayList<NewsfromRssResponse>();
        System.out.println("1111111111111111111111111\n");
        List<NewsfromRssModel> newsfromRssModelList = getNewsfromRssService.getNewsfromRss(region);
        System.out.println("6666666666666666666666666\n");

        for(NewsfromRssModel newsfromRssModel : newsfromRssModelList){
            NewsfromRssResponse newsfromRssResponse=new NewsfromRssResponse(newsfromRssModel);
            newsfromRssResponseList.add(newsfromRssResponse);
        }
        System.out.println(newsfromRssResponseList.size());
        return newsfromRssResponseList;
    }

}