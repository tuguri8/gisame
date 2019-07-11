package me.gisa.api;

import me.gisa.api.datatool.sisemi.SisemeClient;
import me.gisa.api.datatool.sisemi.model.RegionGroup;
import me.gisa.api.datatool.sisemi.model.RegionType;
import me.gisa.api.naver.batch.service.NaverNewsSearchScheduleService;
import me.gisa.api.naver.batch.service.SisemeRegionSearchScheduleService;
import me.gisa.api.naver.repository.NewsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {

    @Autowired
    SisemeClient sisemeClient;
    @Autowired
    SisemeRegionSearchScheduleService sisemeRegionSearchScheduleService;
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    NaverNewsSearchScheduleService naverNewsSearchScheduleService;

    @Test
    public void test() {
        Optional<List<String>> list = sisemeClient.getRegionList(RegionType.SIDO.name())
                                                  .map(regions -> regions.stream()
                                                                         .map(region ->
                                                                                  RegionGroup.findByRegionName(region.getFullName())
                                                                                             .getKeyword())
                                                                         .collect(Collectors.toList()));

        Optional<List<String>> b = sisemeClient.getRegionList(RegionType.SIDO.name())
                                     .map(regions -> regions.stream().map(region -> region.getFullName()).collect(Collectors.toList()));
        b.ifPresent(i-> System.out.println(i));
        list.ifPresent(i -> System.out.println(i));
    }

    @Test
    public void test2(){
        Optional<List<String>> regionFullNameList = sisemeRegionSearchScheduleService.getRegionFullNameList();
        if (regionFullNameList.isPresent()) { return; }

        regionFullNameList.get()
                          .stream()
                          .forEach(regionFullName -> naverNewsSearchScheduleService.getNewsList(regionFullName)
                                                                                   .ifPresent(v1NaverNewsResponse -> naverNewsSearchScheduleService
                                                                                       .insertNaverNews(v1NaverNewsResponse)));
    }
}
