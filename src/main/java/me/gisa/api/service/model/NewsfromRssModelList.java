package me.gisa.api.service.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="rss")
@XmlAccessorType(XmlAccessType.FIELD)
public class NewsfromRssModelList {

    @XmlElementWrapper(name="channel")
    @XmlElement(name = "item")
    public List<NewsfromRssModel> newsfromRssModelList;

    public List<NewsfromRssModel> getNewsfromRssModelList(){
        return newsfromRssModelList;
    }
    public void setNewsfromRssModelList (List<NewsfromRssModel> newsfromRssModelList){
        this.newsfromRssModelList=newsfromRssModelList;
    }

}
