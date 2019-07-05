package me.gisa.api.naver.batch.service;

import me.gisa.api.datatool.sisemi.model.Region;

import java.util.List;
import java.util.Optional;

public interface SisemeRegionSearchScheduleService {

    //siseme api 호출
    Optional<List<Region>> getRegionList(String regionType);

    //지역명 변경규칙 적용
    List<String> getRegionNameList(Optional<List<Region>> optionalRegions);

}
