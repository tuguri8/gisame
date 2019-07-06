package me.gisa.api.daum.service;

import me.gisa.api.daum.datatool.daum.DaumSearchClient;
import me.gisa.api.daum.datatool.daum.model.DaumSearchResponse;
import me.gisa.api.daum.datatool.api.SisemeClient;
import me.gisa.api.daum.datatool.api.model.Region;
import me.gisa.api.daum.service.model.DaumResultmodel;
import me.gisa.api.daum.service.model.SisemeResultModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServicempl implements NewsService{
    private final SisemeClient sisemeClient;
    private final DaumSearchClient daumSearchClient;

    public NewsServicempl(SisemeClient sisemeClient, DaumSearchClient daumSearchClient) {
        this.sisemeClient = sisemeClient;
        this.daumSearchClient = daumSearchClient;
    }

    @Override
    public List<SisemeResultModel> getSisemeResult(String regionName) {
        return sisemeClient.getRegionList(regionName).get().stream().map(x -> transform(x)).collect(Collectors.toList());
    }

    @Override
    public List<DaumResultmodel> getDaumResult(String query) {
        return daumSearchClient.getNews(query, "recency").getDocuments().stream().map(x -> daumTransform(x)).collect(Collectors.toList());
    }

    private SisemeResultModel transform(Region region) {
        SisemeResultModel sisemeResultModel = new SisemeResultModel();
        sisemeResultModel.setType(region.getType());
        sisemeResultModel.setCode(region.getCode());
        sisemeResultModel.setKeyword(changeRegionName(region.getFullName()));
        return sisemeResultModel;
    }

    private DaumResultmodel daumTransform(DaumSearchResponse.Documents document) {
        DaumResultmodel daumResultmodel= new DaumResultmodel();
        daumResultmodel.setTitle(document.getTitle());
        daumResultmodel.setContents(document.getContents());
        daumResultmodel.setDatetime(document.getDatetime());
        daumResultmodel.setUrl(document.getUrl());
        return daumResultmodel;
    }

    private String changeRegionName(String regionName) {
        if (regionName.contains("특별")) {
            return regionName.substring(0,2)+'시';
        } else if(regionName.contains("광역")) {
            return regionName.substring(0,2)+'시';
        } else if(regionName.contains("남도") || regionName.contains("북도")) {
            return new StringBuilder().append(regionName.charAt(0)).append(regionName.charAt(2)).toString();
        }
        return regionName;
    }
}
