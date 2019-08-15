package me.gisa.api.service.model;

import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.service.model.Tag;

import java.util.Map;

public class NewsDto {

    private String baseUrl;
    private Tag tag;
    private NewsType newsType;
    private String regionCode;

    private Map<String, String> parameters;

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @Override
    public String toString() {
        return "NewsDto{" +
            "baseUrl='" + baseUrl + '\'' +
            ", tag=" + tag +
            ", newsType=" + newsType +
            ", parameters=" + parameters +
            '}';
    }
}
