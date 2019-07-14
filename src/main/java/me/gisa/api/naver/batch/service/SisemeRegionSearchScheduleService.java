package me.gisa.api.naver.batch.service;

import me.gisa.api.datatool.siseme.model.Region;
import me.gisa.api.datatool.siseme.model.RegionType;

import java.util.List;
import java.util.Optional;

public interface SisemeRegionSearchScheduleService {

    Optional<List<Region>> getRegionList(RegionType regionType);

    Optional<List<String>> getRegionFullNameList();

//    Optional<List<Region>> getRegionList(String regionType);
//    List<String> getRegionNameList(Optional<List<Region>> optionalRegions);

}
