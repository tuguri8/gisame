package me.gisa.api.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="item")
@XmlAccessorType(XmlAccessType.FIELD)
public class NewsfromRssModel {

    @XmlElement(name="title")
    public String title;

    @XmlElement(name="link")
    public String originallick;

    @XmlElement(name="pubDate")
    public String pubDate;

}
