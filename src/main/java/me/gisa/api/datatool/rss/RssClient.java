package me.gisa.api.datatool.rss;

import me.gisa.api.datatool.rss.model.v1.V1RssNewsResponse;
import me.gisa.api.service.model.NewsModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FeignClient(name = "rss-client")
public interface RssClient {
    //google rss에서 직접 뉴스기사 가져옴
    @GetMapping("api/rss/{region}")
   List<V1RssNewsResponse> getNewsFromRss(@PathVariable String region) throws URISyntaxException, MalformedURLException, JAXBException, UnsupportedEncodingException ;

}
