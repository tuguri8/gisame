package me.gisa.api.rss.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Document {

    @XmlElement(name="title")
    public String title;

    @XmlElement(name="link")
    public String originallick;

    @XmlElement(name="pubDate")
    public String pubDate;

    @XmlElement(name="description")
    public String summary;

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getOriginallick(){
        return originallick;
    }
    public void setOriginallick(String originallick){
        this.originallick = originallick;
    }

    public String getPubDate(){
        return pubDate;
    }
    public void setPubDate(String pubDate){
        this.pubDate = pubDate;
    }

    public String getSummary(){
        return summary;
    }
    public void setSummary(String summary){
        this.summary = summary;
    }

}
