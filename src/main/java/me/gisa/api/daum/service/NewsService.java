package me.gisa.api.daum.service;

import me.gisa.api.daum.service.model.DaumResultmodel;
import me.gisa.api.daum.service.model.SisemeResultModel;

import java.util.List;

public interface NewsService {
    List<SisemeResultModel> getSisemeResult(String regionName);

    List<DaumResultmodel> getDaumResult(String query);
}
