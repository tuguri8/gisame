package me.gisa.api.naver.batch.service;

import me.gisa.api.datatool.sisemi.SisemeClient;
import me.gisa.api.datatool.sisemi.model.Region;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

@Service
public class SisemeRegionSearchScheduleServiceImpl implements SisemeRegionSearchScheduleService {

    private final SisemeClient sisemeClient;

    public SisemeRegionSearchScheduleServiceImpl(SisemeClient sisemeClient) {this.sisemeClient = sisemeClient;}

    @Override
    public Optional<List<Region>> getRegionList(String regionType) {
        return sisemeClient.getRegionList(regionType);
    }

    @Override
    public List<String> getRegionNameList(Optional<List<Region>> optionalRegions) {

        List<String> regionNameList = new ArrayList<>();
        optionalRegions.get().forEach(region -> {
            String regionName = convToRegionName(region.getFullName(), "부동산");
            regionNameList.add(regionName);
        });
        return regionNameList;
    }

    private String convToRegionName(String fullName, String searchKeyword) {

        StringTokenizer st = new StringTokenizer(fullName);
        String sido = st.nextToken();

        if (sido.contains("특별")) {
            sido = sido.substring(0, sido.indexOf("특별"));
        } else if (sido.contains("광역")) {
            sido = sido.substring(0, sido.indexOf("광역"));
        } else if (sido.contains("북도")) {
            sido = sido.charAt(0) + "북";
        } else if (sido.contains("남도")) {
            sido = sido.charAt(0) + "남";
        }
        return sido + " " + (st.hasMoreTokens() ? st.nextToken() : "") + " " + searchKeyword;
    }
}
