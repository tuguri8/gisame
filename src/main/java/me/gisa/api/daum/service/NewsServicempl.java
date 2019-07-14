package me.gisa.api.daum.service;

import me.gisa.api.daum.datatool.siseme.model.RegionGroup;
import me.gisa.api.daum.datatool.siseme.model.RegionType;
import me.gisa.api.daum.datatool.daum.DaumSearchClient;
import me.gisa.api.daum.datatool.daum.model.DaumSearchResponse;
import me.gisa.api.daum.datatool.siseme.SisemeClient;
import me.gisa.api.daum.datatool.siseme.model.Region;
import me.gisa.api.daum.repository.entity.DaumNews;
import org.apache.commons.collections4.ListUtils;
import me.gisa.api.daum.repository.entity.DaumNewsRepository;
import me.gisa.api.daum.service.model.DaumResultmodel;
import me.gisa.api.daum.service.model.NewsBySisemeModel;
import me.gisa.api.daum.service.model.SisemeResultModel;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Service
public class NewsServicempl implements NewsService {
    private final SisemeClient sisemeClient;
    private final DaumSearchClient daumSearchClient;
    private final DaumNewsRepository daumNewsRepository;
    private final ModelMapper modelMapper;

    public NewsServicempl(SisemeClient sisemeClient,
                          DaumSearchClient daumSearchClient,
                          DaumNewsRepository daumNewsRepository, ModelMapper modelMapper) {
        this.sisemeClient = sisemeClient;
        this.daumSearchClient = daumSearchClient;
        this.daumNewsRepository = daumNewsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List getSisemeResult(RegionType regionType) {
        Optional<List<Region>> sisemeResult = sisemeClient.getRegionList(regionType.name());
        return sisemeResult.map(regions -> regions
            .stream()
            .map(this::transform)
            .collect(Collectors.toList())).orElse(Collections.EMPTY_LIST);
    }

    @Override
    public List<DaumResultmodel> getDaumResult(String query) {
        return daumSearchClient.getNews(query, "recency")
                               .getDocuments()
                               .stream()
                               .map(document -> modelMapper.map(document, DaumResultmodel.class))
                               .collect(Collectors.toList());
    }

    @Override
    public List<DaumNews> getDaumResult(SisemeResultModel sisemeResultModel) {
        return daumSearchClient.getNews(sisemeResultModel.getKeyword(), "recency")
                               .getDocuments()
                               .stream()
                               .map(documents -> transform(sisemeResultModel, documents))
                               .collect(Collectors.toList());
    }

    @Override
    @Scheduled(cron = "0 0 0/1 * * *")
    public void getNewsBySiseme() {
        List<SisemeResultModel> sisemeResultModelList = ListUtils.union(getSisemeResult(RegionType.SIDO),
                                                                        getSisemeResult(RegionType.GUNGU));
        List<DaumNews> batchResult = sisemeResultModelList.stream()
                                                          .map(this::getDaumResult)
                                                          .flatMap(Collection::stream)
                                                          .collect(Collectors.toList());
        daumNewsRepository.saveAll(batchResult);
        batchResult.stream()
                   .map(x -> modelMapper.map(x, NewsBySisemeModel.class))
                   .collect(Collectors.toList());
    }

    private DaumNews transform(SisemeResultModel sisemeResultModel, DaumSearchResponse.Documents documents) {
        DaumNews daumNews = new DaumNews();
        daumNews.setType(sisemeResultModel.getType());
        daumNews.setCode(sisemeResultModel.getCode());
        daumNews.setKeyword(sisemeResultModel.getKeyword());
        daumNews.setTitle(documents.getTitle());
        daumNews.setDatetime(documents.getDatetime());
        daumNews.setUrl(documents.getUrl());
        daumNews.setContents(documents.getContents());
        return daumNews;
    }

    private SisemeResultModel transform(Region region) {
        StringTokenizer st = new StringTokenizer(region.getFullName());
        String keyword = st.nextToken();
        keyword = st.hasMoreTokens() ? (RegionGroup.findByRegionName(keyword)
                                                   .getKeyword() + " " + st.nextToken()) : RegionGroup.findByRegionName(keyword)
                                                                                                      .getKeyword();
        SisemeResultModel sisemeResultModel = new SisemeResultModel();
        sisemeResultModel.setType(region.getType());
        sisemeResultModel.setCode(region.getCode());
        sisemeResultModel.setKeyword(keyword);
        return sisemeResultModel;
    }
}
