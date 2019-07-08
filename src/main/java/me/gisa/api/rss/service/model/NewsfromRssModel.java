package me.gisa.api.rss.service.model;

public class NewsfromRssModel {
    private String title ;
    private String originalLink;
    private String shortLink;
    private String pubDate;
    private String elapsedDays; //경과일
    private String description ;

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getOriginalLink(){
        return originalLink;
    }
    public void setOriginalLink(String originalLink){
        this.originalLink = originalLink;
    }

    public String getShortLink(){
        return shortLink;
    }
    public void setShortLink(String shortLink){
        this.shortLink = shortLink;
    }

    public String getElapsedDays(){
        return elapsedDays;
    }
    public void setElapsedDays(String elapsedDays) {
        this.elapsedDays = elapsedDays;
    }

    public String getPubDate(){
        return pubDate;
    }
    public void setPubDate(String pubDate){
        this.pubDate = pubDate;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
}
