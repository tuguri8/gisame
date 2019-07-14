package me.gisa.api.daum.datatool.daum;

import me.gisa.api.daum.datatool.daum.model.DaumSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "daum-client", fallbackFactory = DaumSearchClientFallbackFactory.class)
public interface DaumSearchClient {
    @GetMapping(value = "/v2/search/web", headers = "Authorization=key")
    DaumSearchResponse getNews(@RequestParam("query") String query,
                               @RequestParam("sort") String sort);
}
