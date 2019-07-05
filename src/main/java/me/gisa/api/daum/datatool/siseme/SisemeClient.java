package me.gisa.api.daum.datatool.siseme;

import me.gisa.api.daum.datatool.siseme.model.Region;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "siseme-client", fallbackFactory = SisemeClientFallbackFactory.class)
public interface SisemeClient {
    @GetMapping("/dev/api/v1/regions/type/{regionType}")
    Optional<List<Region>> getRegionList(@PathVariable("regionType") String regionType);
}
