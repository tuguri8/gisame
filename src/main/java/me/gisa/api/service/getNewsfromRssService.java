package me.gisa.api.service;

import me.gisa.api.service.model.NewsfromRssModel;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

public interface getNewsfromRssService {
    public List<NewsfromRssModel> getNewsfromRss (String region) throws URISyntaxException, MalformedURLException, JAXBException, UnsupportedEncodingException;
}