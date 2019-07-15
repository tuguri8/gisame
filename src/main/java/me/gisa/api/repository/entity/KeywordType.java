package me.gisa.api.repository.entity;

public enum KeywordType {
    BOODONGSAN("부동산");

    private String keyword;

    KeywordType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword(){
        return keyword;
    }
}
