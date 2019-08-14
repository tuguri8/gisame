package me.gisa.api.datatool.rss.model.v1;

import me.gisa.api.datatool.rss.model.v1.Document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.FIELD)
public class Documents {

    @XmlElementWrapper(name = "channel")
    @XmlElement(name = "item")
    public List<Document> documentList;

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

}
