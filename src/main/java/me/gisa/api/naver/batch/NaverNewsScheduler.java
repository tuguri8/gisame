package me.gisa.api.naver.batch;

import me.gisa.api.naver.batch.service.NaverNewsSearchScheduleService;
import me.gisa.api.naver.batch.service.SisemeRegionSearchScheduleService;
import me.gisa.api.naver.repository.NewsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class NaverNewsScheduler {

    private final NaverNewsSearchScheduleService naverNewsSearchScheduleService;
    private final SisemeRegionSearchScheduleService sisemeRegionSearchScheduleService;
    private final NewsRepository newsRepository;

    public NaverNewsScheduler(NaverNewsSearchScheduleService naverNewsSearchScheduleService,
                              SisemeRegionSearchScheduleService sisemeRegionSearchScheduleService,
                              NewsRepository newsRepository) {
        this.naverNewsSearchScheduleService = naverNewsSearchScheduleService;
        this.sisemeRegionSearchScheduleService = sisemeRegionSearchScheduleService;
        this.newsRepository = newsRepository;
    }

    @Scheduled(cron = "* */2 * * * *")
    public void sync() {
        Optional<List<String>> regionFullNameList = sisemeRegionSearchScheduleService.getRegionFullNameList();
        if (!regionFullNameList.isPresent()) { return; }

        try {
            Thread.sleep(200);
            regionFullNameList.get()
                              .stream()
                              .forEach(regionFullName -> naverNewsSearchScheduleService.getNewsList(regionFullName)
                                                                                       .ifPresent(v1NaverNewsResponse -> naverNewsSearchScheduleService
                                                                                           .insertNaverNews(v1NaverNewsResponse)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
