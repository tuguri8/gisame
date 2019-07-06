package me.gisa.api.service;

import me.gisa.api.service.model.NewsfromRssModel;
import me.gisa.api.service.model.NewsfromRssModelList;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@Service
public class getNewsfromRssServiceImpl implements getNewsfromRssService {

    public List<NewsfromRssModel> getNewsfromRss (String region) throws URISyntaxException, MalformedURLException, JAXBException, UnsupportedEncodingException {
        JAXBContext jaxbContext;
        jaxbContext=JAXBContext.newInstance(NewsfromRssModelList.class);
        Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();

        String koreanParameter = region + " 부동산";
        koreanParameter = URLEncoder.encode(koreanParameter,"UTF-8");
        String uri="https://news.google.com/rss/search?q="
                + koreanParameter +
                "&hl=ko&gl=KR&ceid=KR:ko";
        URL newsuri = new URL(uri);
        System.out.println(newsuri);
        NewsfromRssModelList newsfromRssModelList = (NewsfromRssModelList)unmarshaller.unmarshal(newsuri);
        List<NewsfromRssModel> newsList = newsfromRssModelList.getNewsfromRssModelList();
        return newsList;

    }

}