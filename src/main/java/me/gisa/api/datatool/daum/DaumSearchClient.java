package me.gisa.api.datatool.daum;

import me.gisa.api.datatool.daum.model.DaumSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "daum-client", fallbackFactory = DaumSearchClientFallbackFactory.class)
public interface DaumSearchClient {
    @GetMapping(value = "/v2/search/web", headers = "Authorization=key")
    DaumSearchResponse getNews(@RequestParam("query") String query,
                               @RequestParam("sort") String sort);
}
