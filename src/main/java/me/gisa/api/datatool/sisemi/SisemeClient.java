package me.gisa.api.datatool.sisemi;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import me.gisa.api.datatool.sisemi.model.Region;

@FeignClient(name = "siseme-client", fallbackFactory = SisemeClientFallbackFactory.class)
public interface SisemeClient {
	@GetMapping("/dev/api/v1/regions/type/{regionType}")
	Optional<List<Region>> getRegionList(@PathVariable("regionType") String regionType);
}
