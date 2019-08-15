package me.gisa.api.service.model;

import me.gisa.api.repository.entity.NewsType;

import java.util.HashMap;
import java.util.Map;

public class ParameterFactory {

    public static Map<String, String> getParameters(NewsType newsType, String regionCode, String page) {
        Map<String, String> parameters = new HashMap<>();
        switch (newsType) {
            case NAVER:
                parameters = new NaverParameter().getParametes(regionCode, page);
                break;
            case DAUM:
                parameters = new DaumParameter().getParametes(regionCode, page);
                break;
        }
        return parameters;
    }
}
