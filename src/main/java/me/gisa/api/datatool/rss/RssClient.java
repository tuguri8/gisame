package me.gisa.api.datatool.rss;

import me.gisa.api.datatool.rss.model.NewsfromRssResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "rss-client")
public interface RssClient {
        @GetMapping("rss/search/{regionName}")
        Optional<List<NewsfromRssResponse>> getNewsfromRssList (@PathVariable("regionName") String regionName);
}
