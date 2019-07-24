package me.gisa.api.rss.service;

import me.gisa.api.service.model.NewsModel;
import me.gisa.api.service.model.SisemeResultModel;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

public interface GoogleNewsService {
    public List<NewsModel> getNewsFromRss(SisemeResultModel sisemeResultModel) throws URISyntaxException, MalformedURLException, JAXBException, UnsupportedEncodingException;
    public List<NewsModel> getNewsFromRss(String keyword) throws MalformedURLException, JAXBException, UnsupportedEncodingException;
    public void saveNewsFromRssToDB() throws MalformedURLException, JAXBException, UnsupportedEncodingException, URISyntaxException;

}