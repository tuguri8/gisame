package me.gisa.api.datatool.naver.model;

import java.util.Date;
import java.util.List;

public class NaverNewsResponse {
	
	public static NaverNewsResponse EMPTY = new NaverNewsResponse();

	private Date lastBuildDate;
	private Integer total;
	private Integer start;
	private Integer display;
	private List<NaverNewsItems> items;

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

	public List<NaverNewsItems> getItems() {
		return items;
	}

	public void setItems(List<NaverNewsItems> items) {
		this.items = items;
	}

}
