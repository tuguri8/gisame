package me.gisa.api.repository.entity;

import me.gisa.api.repository.entity.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "NEWS")
public class News extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8295688402595799135L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String content;
    private String originalLink;    //언론사 url
    private String subLink;         //???서브 url?
    private LocalDate pubDate;

    private String regionCode;
    private String summary;


    @Enumerated(EnumType.STRING)
    private String searchKeyword;

    @Enumerated(EnumType.STRING)
    private NewsType newsType;        //NAVER, KAKAO

    @Override
    public String toString() {
        return "News{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", originalLink='" + originalLink + '\'' +
            ", subLink='" + subLink + '\'' +
            ", pubDate=" + pubDate +
            ", regionCode='" + regionCode + '\'' +
            ", searchKeyword='" + searchKeyword + '\'' +
            ", summary='" + summary + '\'' +
            ", newsType='" + newsType + '\'' +
            '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }
}
