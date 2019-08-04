package me.gisa.api.datatool.daum;

import me.gisa.api.datatool.daum.model.v1.V1DaumNewsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "daum-client")
public interface DaumSearchClient {
    @GetMapping(value = "/v2/search/web", headers = "Authorization=KakaoAK b309b51af28c75a5fb05f12a38ca461f")
    V1DaumNewsResponse getNews(@RequestParam("query") String query,
                               @RequestParam("sort") String sort);
}
