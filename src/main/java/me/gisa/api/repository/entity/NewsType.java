package me.gisa.api.repository.entity;

import java.util.Arrays;

public enum NewsType {
    NAVER, DAUM, GOOGLE, UNKNOWN;

    public static NewsType fromString(String newsType) {
        return Arrays.stream(values())
                     .filter(type -> type.name().equalsIgnoreCase(newsType))
                     .findFirst()
                     .orElse(UNKNOWN);
    }
}
