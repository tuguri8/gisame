package me.gisa.api.rss.service;

import lombok.Data;
import me.gisa.api.rss.service.model.Document;
import me.gisa.api.rss.service.model.Documents;
import me.gisa.api.rss.service.model.NewsfromRssModel;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class getNewsfromRssServiceImpl implements getNewsfromRssService {

    public List<NewsfromRssModel> getNewsfromRss (String region) throws URISyntaxException, MalformedURLException, JAXBException, UnsupportedEncodingException {
        JAXBContext jaxbContext;
        jaxbContext=JAXBContext.newInstance(Documents.class);
        Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();

        String koreanParameter = region + " 부동산";
        koreanParameter = URLEncoder.encode(koreanParameter,"UTF-8");
        String uri="https://news.google.com/rss/search?q="
                + koreanParameter +
                "&hl=ko&gl=KR&ceid=KR:ko";
        URL newsuri = new URL(uri);
        Documents documents = (Documents)unmarshaller.unmarshal(newsuri);
        List<Document> newsList = documents.getDocumentList();
        return transform(newsList);

    }

    private List<NewsfromRssModel> transform(List<Document> documents) {
        List<NewsfromRssModel> newsfromRssModelList= new ArrayList<NewsfromRssModel>();
        for(Document document : documents){
            NewsfromRssModel newsfromRssModel=new NewsfromRssModel();
            newsfromRssModel.setTitle(document.getTitle());
            newsfromRssModel.setOriginalLink(document.getOriginallick());
            newsfromRssModel.setDescription(document.getDescription());
            //newsfromRssModel.getShortLink();
            //newsfromRssModel.setElapsedDays();

            newsfromRssModelList.add(newsfromRssModel);
        }
        return newsfromRssModelList;
    }

}