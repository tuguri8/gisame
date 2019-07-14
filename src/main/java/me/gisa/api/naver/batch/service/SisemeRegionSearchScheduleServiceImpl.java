package me.gisa.api.naver.batch.service;

import me.gisa.api.datatool.siseme.SisemeClient;
import me.gisa.api.datatool.siseme.model.Region;
import me.gisa.api.datatool.siseme.model.RegionGroup;
import me.gisa.api.datatool.siseme.model.RegionType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SisemeRegionSearchScheduleServiceImpl implements SisemeRegionSearchScheduleService {

    private final SisemeClient sisemeClient;

    public SisemeRegionSearchScheduleServiceImpl(SisemeClient sisemeClient) {this.sisemeClient = sisemeClient;}

    @Override
    public Optional<List<Region>> getRegionList(RegionType regionType) {
        return sisemeClient.getRegionList(regionType.name());
    }

    @Override
    public Optional<List<String>> getRegionFullNameList() {
        return getRegionList(RegionType.SIDO)
            .map(regions -> regions.stream()
                                   .map(region -> RegionGroup.findByRegionName(region.getFullName())
                                                             .getKeyword())
                                   .collect(Collectors.toList()));
    }

//    @Override
//    public Optional<List<Region>> getRegionList(String regionType) {
//        return sisemeClient.getRegionList(regionType);
//    }
//
//    @Override
//    public List<String> getRegionNameList(Optional<List<Region>> optionalRegions) {
//
//        List<String> regionNameList = new ArrayList<>();
//        optionalRegions.get().forEach(region -> {
//            String regionName = convToRegionName(region.getFullName());
//            regionNameList.add(regionName);
//        });
//        return regionNameList;
//    }

//    private String convToRegionName(String fullName) {
//
//        StringTokenizer st = new StringTokenizer(fullName);
//        String sido = st.nextToken();
//
//        if (sido.contains("특별")) {
//            sido = sido.substring(0, sido.indexOf("특별"));
//        } else if (sido.contains("광역")) {
//            sido = sido.substring(0, sido.indexOf("광역"));
//        } else if (sido.contains("북도")) {
//            sido = sido.charAt(0) + "북";
//        } else if (sido.contains("남도")) {
//            sido = sido.charAt(0) + "남";
//        }
//        return sido + " " + (st.hasMoreTokens() ? st.nextToken() : "");
//    }
}
