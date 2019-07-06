package me.gisa.api.daum.datatool.api;

import me.gisa.api.daum.datatool.api.model.Region;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "api-client", fallbackFactory = SisemeClientFallbackFactory.class)
public interface SisemeClient {
    @GetMapping("/dev/api/v1/regions/type/{regionType}")
    Optional<List<Region>> getRegionList(@PathVariable("regionType") String regionType);
}
