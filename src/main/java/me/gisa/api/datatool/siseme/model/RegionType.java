package me.gisa.api.datatool.siseme.model;

import java.util.Arrays;

public enum RegionType {
    SIDO, GUNGU, DONG, UNKNOWN;

    RegionType() {
    }

    public static RegionType fromString(String regionType) {
        return Arrays.stream(values())
                     .filter(type -> type.name().equalsIgnoreCase(regionType))
                     .findFirst()
                     .orElse(UNKNOWN);
    }
}
