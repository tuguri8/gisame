package me.gisa.api.daum.common;

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
