package me.gisa.api.naver.batch;

import me.gisa.api.datatool.naver.model.v1.V1NaverNewsResponse;
import me.gisa.api.datatool.sisemi.model.Region;
import me.gisa.api.naver.batch.service.NaverNewsSearchScheduleService;
import me.gisa.api.naver.batch.service.SisemeRegionSearchScheduleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class NaverNewsScheduler {

    private final NaverNewsSearchScheduleService naverNewsSearchScheduleService;
    private final SisemeRegionSearchScheduleService sisemeRegionSearchScheduleService;

    public NaverNewsScheduler(NaverNewsSearchScheduleService naverNewsSearchScheduleService,
                              SisemeRegionSearchScheduleService sisemeRegionSearchScheduleService) {
        this.naverNewsSearchScheduleService = naverNewsSearchScheduleService;
        this.sisemeRegionSearchScheduleService = sisemeRegionSearchScheduleService;
    }
    @Scheduled(cron = "* */2 * * * *")
    public void sync(){

        Optional<List<Region>> optionalRegions = sisemeRegionSearchScheduleService.getRegionList("gungu");
        List<String> regionNameList = sisemeRegionSearchScheduleService.getRegionNameList(optionalRegions);
        regionNameList.forEach(regionName->{
            try {
                Thread.sleep(100);
                V1NaverNewsResponse v1NaverNewsResponse = naverNewsSearchScheduleService.searchNaverNews(regionName, "부동산");
                naverNewsSearchScheduleService.insertNaverNews(v1NaverNewsResponse);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
