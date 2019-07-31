package me.gisa.api.controller.model;

import me.gisa.api.repository.entity.NewsType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class PageVO {

    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_MAX_SIZE = 50;

    private int page;
    private int size;
    private String regionCode;
    private KeywordType searchKeyword;
    private NewsType newsType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public PageVO() {
        this.page = 1;
        this.size = DEFAULT_SIZE;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page < 0 ? 1 : page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {

        this.size = size < DEFAULT_SIZE || size > DEFAULT_MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public void setSearchKeyword(KeywordType searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }

    public KeywordType getSearchKeyword() {
        return searchKeyword;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public Pageable makePageable(int direction, String... props) {
        Sort.Direction dir = direction == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(this.page - 1, this.size, dir, props);
    }

    @Override
    public String toString() {
        return "PageVO{" +
            "page=" + page +
            ", size=" + size +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", regionCode='" + regionCode + '\'' +
            ", searchKeyword=" + searchKeyword +
            ", newsType=" + newsType +
            '}';
    }
}
