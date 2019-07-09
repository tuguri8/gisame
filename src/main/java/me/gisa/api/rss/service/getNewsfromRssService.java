package me.gisa.api.rss.service;

import me.gisa.api.rss.service.model.NewsfromRssModel;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

public interface getNewsfromRssService {
    public List<NewsfromRssModel> getNewsfromRss (String region) throws URISyntaxException, MalformedURLException, JAXBException, UnsupportedEncodingException;
    public void saveNewsfromRssToDB(String region) throws MalformedURLException, JAXBException, UnsupportedEncodingException, URISyntaxException;
}