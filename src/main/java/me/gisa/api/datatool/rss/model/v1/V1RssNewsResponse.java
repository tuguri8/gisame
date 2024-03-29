package me.gisa.api.datatool.rss.model.v1;

import me.gisa.api.service.model.NewsModel;

import java.time.LocalDate;

public class V1RssNewsResponse {

    private String title;
    private String originalLink;
    private String summary;
    private LocalDate pubDate;

    public V1RssNewsResponse(NewsModel newsfromRssModel) {
        this.title = newsfromRssModel.getTitle();
        this.originalLink = newsfromRssModel.getOriginalLink();
        this.summary = newsfromRssModel.getSummary();
        this.pubDate = newsfromRssModel.getPubDate();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginallick() {
        return originalLink;
    }

    public void setOriginallick(String originalLink) {
        this.originalLink = originalLink;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDate getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDate pubDate) {
        this.pubDate = pubDate;
    }
}

