package me.gisa.api.naver.repository.entity;

import me.gisa.api.naver.repository.entity.common.BaseEntity;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "NEWS")
public class News extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8295688402595799135L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String content;
    private String path;
    private String webUrl;
    private LocalDate pubDate;
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public LocalDate getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDate pubDate) {
        this.pubDate = pubDate;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("title", title)
                                        .append("content", content)
                                        .append("path", path)
                                        .append("webUrl", webUrl)
                                        .toString();
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
