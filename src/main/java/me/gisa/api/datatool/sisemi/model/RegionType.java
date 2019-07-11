package me.gisa.api.datatool.sisemi.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum RegionType {

    SIDO,GUNGU,DONG,UNKNOWN;

    public static RegionType getRegionType(String regionType){
        return Arrays.stream(values()).filter(region -> region.name().equalsIgnoreCase(regionType)).findFirst().orElse(UNKNOWN);
    }

    public static List<RegionType> getRegionTypeList() {
        return Arrays.asList(values()).stream()
                     .filter(regionType -> !regionType.name().equals(RegionType.UNKNOWN.name()))
                     .collect(Collectors.toList());
    }
}
