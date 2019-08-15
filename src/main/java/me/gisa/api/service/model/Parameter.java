package me.gisa.api.service.model;

import java.util.Map;

public abstract class Parameter {
    public abstract Map<String, String> getParametes(String sido, String page);
}
