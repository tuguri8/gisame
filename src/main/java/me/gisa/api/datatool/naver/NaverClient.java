package me.gisa.api.datatool.naver;

import me.gisa.api.datatool.naver.model.V1NaverNewsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "naver-client")
public interface NaverClient {

    @GetMapping(value = "/v1/search/news.json")
    Optional<V1NaverNewsResponse> getNewsList(@RequestParam("query") String query);

}
