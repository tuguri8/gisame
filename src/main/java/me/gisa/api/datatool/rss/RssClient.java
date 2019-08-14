package me.gisa.api.datatool.rss;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import me.gisa.api.datatool.rss.model.v1.V1RssNewsResponse;

@FeignClient(name = "rss-client")
public interface RssClient {
    //google rss에서 직접 뉴스기사 가져옴
    @GetMapping("api/rss/{region}")
   List<V1RssNewsResponse> getNewsFromRss(@PathVariable("region") String region) throws URISyntaxException, MalformedURLException, JAXBException, UnsupportedEncodingException ;

}
