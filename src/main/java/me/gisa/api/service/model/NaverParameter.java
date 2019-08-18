package me.gisa.api.service.model;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class NaverParameter extends Parameter {

    @Override
    public Map<String, String> getParametes(String regionCode, String page) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("city_no", makeCityNo(regionCode));
        parameters.put("dvsn_no", "");
        parameters.put("page", page);
        return parameters;
    }

    private String makeCityNo(String sido) {
        return StringUtils.rightPad(sido, 10, "0");
    }

}
