package me.gisa.api.rss.service;

import lombok.Data;
import me.gisa.api.rss.repository.entity.Newsfromrss;
import me.gisa.api.rss.repository.NewsfromrssRepository;
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
private final NewsfromrssRepository newsfromrssRepository;

public getNewsfromRssServiceImpl(NewsfromrssRepository newsfromrssRepository){
    this.newsfromrssRepository=newsfromrssRepository;
}
    //google rss에서 가져옴
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
    //가져온 뉴스기사들 DB에 저장
    public void saveNewsfromRssToDB(String region) throws MalformedURLException, JAXBException, UnsupportedEncodingException, URISyntaxException {
        List<NewsfromRssModel> newsfromRssModelList = getNewsfromRss(region);

        for(NewsfromRssModel newsfromRssModel : newsfromRssModelList){
            Newsfromrss newsfromrss = new Newsfromrss();

            newsfromrss.setTitle(newsfromRssModel.getTitle());
            newsfromrss.setLink(newsfromRssModel.getOriginalLink());
            newsfromrss.setDescription(newsfromRssModel.getDescription());
            newsfromrss.setPubDate(newsfromRssModel.getPubDate());
            newsfromrss.setRegionName(region);

            newsfromrssRepository.save(newsfromrss);
        }
    }
    private List<NewsfromRssModel> transform(List<Document> documents) {
        List<NewsfromRssModel> newsfromRssModelList= new ArrayList<NewsfromRssModel>();
        for(Document document : documents){
            NewsfromRssModel newsfromRssModel=new NewsfromRssModel();
            newsfromRssModel.setTitle(document.getTitle());
            newsfromRssModel.setPubDate(document.getPubDate());
            newsfromRssModel.setOriginalLink(document.getOriginallick());
            newsfromRssModel.setDescription(document.getDescription());
            //newsfromRssModel.getShortLink();
            //newsfromRssModel.setElapsedDays();

            newsfromRssModelList.add(newsfromRssModel);
        }
        return newsfromRssModelList;
    }

}