package me.gisa.api.rss.service;

import me.gisa.api.datatool.siseme.SisemeClient;
import me.gisa.api.rss.repository.entity.Newsfromrss;
import me.gisa.api.rss.repository.NewsfromrssRepository;
import me.gisa.api.rss.service.model.Document;
import me.gisa.api.rss.service.model.Documents;
import me.gisa.api.rss.service.model.NewsfromRssModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class getNewsfromRssServiceImpl implements getNewsfromRssService {
private final NewsfromrssRepository newsfromrssRepository;
private final SisemeClient sisemeClient;

public getNewsfromRssServiceImpl(NewsfromrssRepository newsfromrssRepository,SisemeClient sisemeClient){
    this.newsfromrssRepository=newsfromrssRepository;
    this.sisemeClient=sisemeClient;
}
    //google rss에서 가져옴
    public List<NewsfromRssModel> getNewsfromRss (String region) throws  MalformedURLException, JAXBException, UnsupportedEncodingException {
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
    public void saveNewsfromRssToDB(String region) throws MalformedURLException, JAXBException, UnsupportedEncodingException {
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
    //하루 단위로 기사 데이터베이스 저장하기
    @Scheduled(cron = "* * * */1 * *")
    public void SaveNewsfromRssScheduling() throws UnsupportedEncodingException, JAXBException, MalformedURLException {
        //sismeclient를 통해 전체 지역 리스트를 string으로 받아온다고 가정 ex) 경기도 광명시 소하동 , 서울시 영등포구 신길동...
        List<String> regionName =new ArrayList<String>() ; //일단 지금은 값이 없는 상태

        for(String regionname : regionName){
            saveNewsfromRssToDB(regionname);
        }
    }

    public String changeRegionName(String regionFullName){
        String regionName;
        String regionName_sido;
        StringBuilder stringBuilder =new StringBuilder();

        if(regionFullName.contains("특별시")||regionFullName.contains("광역시")){
           regionName = regionFullName.substring(0,2);
           regionName_sido = regionFullName.substring(0,2) + "시";

           return regionName;
        }
        else if (regionFullName.contains("남도")||regionFullName.contains("북도")){
            regionName=stringBuilder.append(regionFullName.charAt(0)).append(regionFullName.charAt(2)).toString();
                    //nregionFullName.charAt(0)+regionFullName.charAt(2);
        }
        else if(regionFullName.contains("특별자치도")){
            regionName = regionFullName.substring(0,2);
            regionName_sido = regionFullName.substring(0,2) + "도";

            return regionName;
        }
        return regionFullName;
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