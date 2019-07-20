package me.gisa.api.rss.service;

import me.gisa.api.rss.service.model.NewsFromRssModel;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

public interface GoogleNewsService {
    public List<NewsFromRssModel> getNewsFromRss(String region) throws URISyntaxException, MalformedURLException, JAXBException, UnsupportedEncodingException;

    public void saveNewsFromRssToDB(String region) throws MalformedURLException, JAXBException, UnsupportedEncodingException, URISyntaxException;

}