package me.gisa.api.datatool.rss.model;


import me.gisa.api.rss.service.model.NewsfromRssModel;

public class NewsfromRssResponse {

    private String title ;
    private String originalLink;
    private String elapsedDays; //경과일
    private String description ;

    public NewsfromRssResponse(NewsfromRssModel newsfromRssModel) {
        System.out.println(1);
        this.title=newsfromRssModel.getTitle();
        System.out.println(2);
        this.originalLink=newsfromRssModel.getOriginalLink();
        System.out.println(3);
        this.elapsedDays=newsfromRssModel.getElapsedDays();
        System.out.println(4);
        this.description=newsfromRssModel.getDescription();
        System.out.println(5);
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

