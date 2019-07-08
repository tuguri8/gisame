package me.gisa.api.rss.service.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="rss")
@XmlAccessorType(XmlAccessType.FIELD)
public class Documents {

    @XmlElementWrapper(name="channel")
    @XmlElement(name = "item")
    public List<Document> documentList;

    public List<Document> getDocumentList(){
        return documentList;
    }
    public void setDocumentList(List<Document> documentList){
        this.documentList = documentList;
    }

}
