package me.gisa.api.datatool.naver.model.v1;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

public class V1NaverNewsResponse {

    public static V1NaverNewsResponse EMPTY = new V1NaverNewsResponse();

    private Date lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<V1NaverNewsItems> items;

    public Date getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(Date lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public List<V1NaverNewsItems> getItems() {
        return items;
    }

    public void setItems(List<V1NaverNewsItems> items) {
        this.items = items;
    }

}
