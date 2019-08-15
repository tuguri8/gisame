package me.gisa.api.service.model;

import me.gisa.api.repository.entity.KeywordType;
import me.gisa.api.repository.entity.NewsType;

import java.time.LocalDate;

public class NewsModel {
    private String title;
    private String content;
    private String originalLink;
    private String thumbnail;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    @Override
    public String toString() {
        return "NewsModel{" +
            "title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", originalLink='" + originalLink + '\'' +
            ", thumbnail='" + thumbnail + '\'' +
            ", pubDate=" + pubDate +
            ", regionCode='" + regionCode + '\'' +
            ", summary='" + summary + '\'' +
            ", searchKeyword=" + searchKeyword +
            ", newsType=" + newsType +
            '}';
    }
}
