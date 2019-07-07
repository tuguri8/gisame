package me.gisa.api.daum.service;

import me.gisa.api.daum.controller.v1.dto.BatchResponse;
import me.gisa.api.daum.repository.entity.DaumNews;
import me.gisa.api.daum.service.model.DaumResultmodel;
import me.gisa.api.daum.service.model.NewsBySisemeModel;
import me.gisa.api.daum.service.model.SisemeResultModel;

import java.util.List;

public interface NewsService {
    List<SisemeResultModel> getSisemeResult(String regionName);

    List<DaumResultmodel> getDaumResult(String query);

    List<DaumNews> getDaumResult(SisemeResultModel sisemeResultModel);

    List<NewsBySisemeModel> getNewsBySiseme();
}
