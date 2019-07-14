package me.gisa.api.rss.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="NEWS_FROM_RSS")
public class Newsfromrss {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    @Lob
    private String description;
    private String pubDate;
    private String link;
    private String regionName;

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getPubDate(){
        return pubDate;
    }
    public void setPubDate(String pubDate){
        this.pubDate = pubDate;
    }

    public String getLink(){
        return link;
    }
    public void setLink(String link){
        this.link = link;
    }

    public String getRegionName(){
        return regionName;
    }
    public void setRegionName(String regionName){
        this.regionName = regionName;
    }
}
