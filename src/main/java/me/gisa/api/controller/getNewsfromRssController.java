package me.gisa.api.controller;

import me.gisa.api.service.getNewsfromRssService;
import me.gisa.api.service.model.NewsfromRssModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class getNewsfromRssController {
    private final getNewsfromRssService getNewsfromRssService;

    public getNewsfromRssController(getNewsfromRssService getNewsfromRssService){
        this.getNewsfromRssService=getNewsfromRssService;
    }

    @GetMapping("/{region}")
    @ResponseBody
    public List<NewsfromRssModel> getNewsFromRss (@PathVariable String region) throws URISyntaxException, MalformedURLException, JAXBException, UnsupportedEncodingException {
        return getNewsfromRssService.getNewsfromRss(region);
    }

}