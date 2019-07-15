package me.gisa.api.repository.entity;

import java.util.Arrays;

public enum KeywordType {
    BOODONGSAN("부동산"), UNKNOWN("");

    private String keyword;

    KeywordType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public static KeywordType fromString(String keywordName) {
        return Arrays.stream(KeywordType.values())
                     .filter(keywordType -> keywordName.equals(keywordType.getKeyword()))
                     .findAny()
                     .orElse(UNKNOWN);
    }
}
