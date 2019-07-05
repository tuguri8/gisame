package me.gisa.api.datatool.naver;

import me.gisa.api.datatool.naver.model.v1.V1NaverNewsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

//@FeignClient(name="naver-client", fallbackFactory = NaverClientFallbackFactory.class ,configuration = NaverClientConfiguration.class)
@FeignClient(name="naver-client", fallbackFactory = NaverClientFallbackFactory.class)
public interface NaverClient {

//    @GetMapping("/v1/search/news.json")
//    Optional<V1NaverNewsResponse> getNewsList(@RequestParam("query") String query);

    @GetMapping("/v1/search/news.json")
    V1NaverNewsResponse getNewsList(@RequestHeader("X-Naver-Client-Id") String clientId,
                                              @RequestHeader("X-Naver-Client-Secret") String clientSecret,
                                              @RequestParam("query") String query);

}
