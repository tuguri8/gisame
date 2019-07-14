package me.gisa.api.datatool.siseme.model;

import java.util.Arrays;

public enum RegionGroup {
    SEOUL("서울특별시", "서울시"),
    SEJONG("세종특별시", "세종시"),
    JEJU("제주특별자치도", "제주도"),
    BUSAN("부산광역시", "부산시"),
    DAEGU("대구광역시", "대구시"),
    INCHEON("인천광역시","인천시"),
    GWANGJU("광주광역시", "광주시"),
    DAEJEON("대전광역시", "대전시"),
    ULSAN("울산광역시", "울산시"),
    JEONBUK("전라북도", "전북"),
    JEONNAM("전라남도", "전남"),
    CHUNGBUK("충청북도","충북"),
    CHUNGNAM("충청남도", "충남"),
    GYEONGBUK("경상북도", "경북"),
    GYEONGNAM("경상남도", "경남"),
    GANGWON("강원도","강원도"),
    GYEONGGI("경기도", "경기도"),
    UNKNOWN;

    private String fullName;
    private String keyword;

    RegionGroup(String fullName, String keyword) {
        this.fullName = fullName;
        this.keyword = keyword;
    }

    RegionGroup() {

    }

    public String getFullName() {
        return fullName;
    }

    public String getKeyword() {
        return keyword;
    }

    public static RegionGroup findByRegionName(String regionName) {
        return Arrays.stream(RegionGroup.values())
            .filter(regionGroup -> regionName.equals(regionGroup.getFullName()))
            .findAny()
            .orElse(SEOUL);
    }
}
