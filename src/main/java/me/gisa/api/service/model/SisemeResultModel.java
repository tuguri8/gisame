package me.gisa.api.service.model;

import me.gisa.api.repository.entity.KeywordType;

public class SisemeResultModel {
    private String type;
    private String code;
    private String keyword;
    private String crawlKeyword;
    private KeywordType searchKeyword;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public KeywordType getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(KeywordType searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getCrawlKeyword() {
        return crawlKeyword;
    }

    public void setCrawlKeyword(String crawlKeyword) {
        this.crawlKeyword = crawlKeyword;
    }
}
