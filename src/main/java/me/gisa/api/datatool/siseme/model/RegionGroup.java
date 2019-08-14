package me.gisa.api.datatool.siseme.model;

import java.util.Arrays;

public enum RegionGroup {
    SEOUL("서울특별시", "서울시", "seoul"),
    SEJONG("세종특별시", "세종시", "sejong"),
    SEJONG2("세종특별자치시", "세종시", "sejong"),
    JEJU("제주특별자치도", "제주도", "jeju"),
    BUSAN("부산광역시", "부산시", "busan"),
    DAEGU("대구광역시", "대구시", "daegu"),
    INCHEON("인천광역시", "인천시", "incheon"),
    GWANGJU("광주광역시", "광주시", "gwangju"),
    DAEJEON("대전광역시", "대전시", "daejeon"),
    ULSAN("울산광역시", "울산시", "ulsan"),
    JEONBUK("전라북도", "전북", "jeolla"),
    JEONNAM("전라남도", "전남", "jeolla"),
    CHUNGBUK("충청북도", "충북", "chungcheong"),
    CHUNGNAM("충청남도", "충남", "chungcheong"),
    GYEONGBUK("경상북도", "경북", "gyeongsang"),
    GYEONGNAM("경상남도", "경남", "gyeongsang"),
    GANGWON("강원도", "강원도", "gangwon"),
    GYEONGGI("경기도", "경기도", "gyeonggi"),
    UNKNOWN;

    private String fullName;
    private String keyword;
    private String crawlKeyword;

    RegionGroup(String fullName, String keyword, String crawlKeyword) {
        this.fullName = fullName;
        this.keyword = keyword;
        this.crawlKeyword = crawlKeyword;
    }

    RegionGroup() {

    }

    public String getFullName() {
        return fullName;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getCrawlKeyword() {
        return crawlKeyword;
    }

    public static RegionGroup findByRegionName(String regionName) {
        return Arrays.stream(RegionGroup.values())
            .filter(regionGroup -> regionName.equals(regionGroup.getFullName()))
            .findAny()
            .orElse(UNKNOWN);
    }
}
