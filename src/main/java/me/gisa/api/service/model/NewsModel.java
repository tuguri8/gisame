package me.gisa.api.service.model;

import me.gisa.api.repository.entity.NewsType;

import java.time.LocalDate;

public class NewsModel {
    private String title;
    private String content;
    private String originalLink;
    private String subLink;
    private LocalDate pubDate;

    private String regionCode;
    private String summary;

    private KeywordType searchKeyword;
    private NewsType newsType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOriginalLink() {
        return originalLink;
    }

    public void setOriginalLink(String originalLink) {
        this.originalLink = originalLink;
    }

    public String getSubLink() {
        return subLink;
    }

    public void setSubLink(String subLink) {
        this.subLink = subLink;
    }

    public LocalDate getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDate pubDate) {
        this.pubDate = pubDate;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public KeywordType getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(KeywordType searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }
}
