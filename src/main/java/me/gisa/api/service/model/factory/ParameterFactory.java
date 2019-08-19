package me.gisa.api.service.model.factory;

import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.service.model.NaverParameter;

import java.util.Map;

public class ParameterFactory {

    public static Map<String, String> getParameters(NewsType newsType, String regionCode, String page) {
        return new NaverParameter().getParametes(regionCode, page);
    }
}
