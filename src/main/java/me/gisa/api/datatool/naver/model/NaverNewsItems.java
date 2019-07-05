package me.gisa.api.datatool.naver.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

public class NaverNewsItems {

	private String title;
	private String originallink;
	private String link;
	private String description;
	private Date pubDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginallink() {
		return originallink;
	}

	public void setOriginallink(String originallink) {
		this.originallink = originallink;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("title", title).append("originallnk", originallink).append("link",link)
			.append("description", description).append("pubDate",pubDate).toString();
	}
}
