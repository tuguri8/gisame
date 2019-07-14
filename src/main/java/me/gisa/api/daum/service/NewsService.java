package me.gisa.api.daum.service;

import me.gisa.api.datatool.siseme.model.RegionType;
import me.gisa.api.daum.repository.entity.DaumNews;
import me.gisa.api.daum.service.model.DaumResultmodel;
import me.gisa.api.daum.service.model.SisemeResultModel;

import java.util.List;

public interface NewsService {
    List getSisemeResult(RegionType regionType);

    List<DaumResultmodel> getDaumResult(String query);

    List<DaumNews> getDaumResult(SisemeResultModel sisemeResultModel);

    void getNewsBySiseme();
}
