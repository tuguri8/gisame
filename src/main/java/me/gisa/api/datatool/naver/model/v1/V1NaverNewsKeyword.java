package me.gisa.api.datatool.naver.model.v1;

import java.util.Arrays;

public enum V1NaverNewsKeyword {

    BUDONGSAN("부동산"), JEONSE("전세"), WALLSE("월세"), UNKNOWN;

    private String keyword;

    private V1NaverNewsKeyword(String keyword) {
        this.keyword = keyword;
    }

    private V1NaverNewsKeyword() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public static V1NaverNewsKeyword getKeyword(String keyword) {
        return Arrays.stream(V1NaverNewsKeyword.values())
                     .filter(naverKeyword -> keyword.equalsIgnoreCase(naverKeyword.getKeyword())).findAny().orElse(UNKNOWN);
    }

    public static String getKeywordList() {
        return new StringBuffer().append(V1NaverNewsKeyword.BUDONGSAN.getKeyword() + " ")
                                 .append(V1NaverNewsKeyword.JEONSE.getKeyword() + " ")
                                 .append(V1NaverNewsKeyword.WALLSE.getKeyword()).toString();
    }
}
