package me.gisa.api.service.model;

import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.service.model.dto.NewsDto;

public class NewsDtoFactory {

    public static NewsDto getNewsDto(String baseUrl, NewsType newsType, String regionCode, String page) {
        NewsDto newsDto = new NewsDto();
        switch (newsType) {
            case NAVER:
                newsDto = getNaverNewsDto(baseUrl, newsType, regionCode, page);
                break;
            case DAUM:
                newsDto = getDaumNewsDto(baseUrl, newsType, regionCode, page);
                break;
        }
        return newsDto;
    }

    private static NewsDto getNaverNewsDto(String baseUrl, NewsType newsType, String regionCode, String page) {
        NewsDto newsDto = new NewsDto();
        newsDto.setNewsType(newsType);
        newsDto.setRegionCode(regionCode);
        newsDto.setTag(TagFactory.getTag(newsType));
        newsDto.setBaseUrl(baseUrl);
        newsDto.setParameters(ParameterFactory.getParameters(newsType, regionCode, page));
        return newsDto;
    }

    private static NewsDto getDaumNewsDto(String baseUrl, NewsType newsType, String regionCode, String page) {
        NewsDto newsDto = new NewsDto();
        newsDto.setNewsType(newsType);
        newsDto.setRegionCode(regionCode);
        newsDto.setTag(TagFactory.getTag(newsType));
        newsDto.setBaseUrl(baseUrl);
        newsDto.setParameters(ParameterFactory.getParameters(newsType, regionCode, page));
        return newsDto;
    }

}
