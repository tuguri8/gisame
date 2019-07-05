package me.gisa.api.datatool.naver;

import me.gisa.api.datatool.naver.model.NaverNewsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

//@FeignClient(name="naver-client", fallbackFactory = NaverClientFallbackFactory.class ,configuration = NaverClientConfiguration.class)
@FeignClient(name="naver-client", fallbackFactory = NaverClientFallbackFactory.class)
public interface NaverClient {

//    @GetMapping("/v1/search/news.json")
//    Optional<NaverNewsResponse> getNewsList(@RequestParam("query") String query);

    @GetMapping("/v1/search/news.json")
    Optional<NaverNewsResponse> getNewsList(@RequestHeader("X-Naver-Client-Id") String clientId,
                                            @RequestHeader("X-Naver-Client-Secret") String clientSecret,
                                            @RequestParam("query") String query);

}
