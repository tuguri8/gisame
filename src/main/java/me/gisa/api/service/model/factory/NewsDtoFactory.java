package me.gisa.api.service.model.factory;

import me.gisa.api.datatool.siseme.model.Region;
import me.gisa.api.datatool.siseme.model.RegionGroup;
import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.service.model.TagFactory;
import me.gisa.api.service.model.NewsDto;

import java.util.Collections;

public class NewsDtoFactory {

    public static NewsDto getNewsDto(String baseUrl, NewsType newsType, Region region, String page) {
        NewsDto newsDto = new NewsDto();
        switch (newsType) {
            case NAVER:
                newsDto = getNaverNewsDto(baseUrl, newsType, region, page);
                break;
            case DAUM:
                newsDto = getDaumNewsDto(baseUrl, newsType, region, page);
                break;
        }
        return newsDto;
    }

    private static NewsDto getNaverNewsDto(String baseUrl, NewsType newsType, Region region, String page) {
        NewsDto newsDto = new NewsDto();
        newsDto.setNewsType(newsType);
        newsDto.setRegionCode(region.getCode());
        newsDto.setTag(TagFactory.getTag(newsType));
        newsDto.setBaseUrl(baseUrl);
        newsDto.setParameters(ParameterFactory.getParameters(newsType, region.getCode(), page));
        return newsDto;
    }

    private static NewsDto getDaumNewsDto(String baseUrl, NewsType newsType, Region region, String page) {
        NewsDto newsDto = new NewsDto();
        newsDto.setNewsType(newsType);
        newsDto.setRegionCode(region.getCode());
        newsDto.setTag(TagFactory.getTag(newsType));
        newsDto.setBaseUrl(baseUrl + "/" + RegionGroup.findByRegionName(region.getFullName()).getCrawlKeyword());
        newsDto.setParameters(Collections.EMPTY_MAP);
        return newsDto;
    }

}
