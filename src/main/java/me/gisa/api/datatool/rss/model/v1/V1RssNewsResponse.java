package me.gisa.api.datatool.rss.model.v1;


import me.gisa.api.rss.service.model.NewsfromRssModel;

public class V1RssNewsResponse {

    private String title ;
    private String originalLink;
    private String elapsedDays; //경과일
    private String description ;

    public V1RssNewsResponse(NewsfromRssModel newsfromRssModel) {
        this.title=newsfromRssModel.getTitle();
        this.originalLink=newsfromRssModel.getOriginalLink();
        this.elapsedDays=newsfromRssModel.getElapsedDays();
        this.description=newsfromRssModel.getDescription();
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getOriginallick(){
        return originalLink;
    }
    public void setOriginallick(String originalLink){
        this.originalLink = originalLink;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
}

