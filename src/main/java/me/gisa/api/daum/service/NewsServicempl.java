package me.gisa.api.daum.service;

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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Service
public class NewsServicempl implements NewsService{
    private final SisemeClient sisemeClient;
    private final DaumSearchClient daumSearchClient;
    private final DaumNewsRepository daumNewsRepository;

    public NewsServicempl(SisemeClient sisemeClient,
                          DaumSearchClient daumSearchClient,
                          DaumNewsRepository daumNewsRepository) {
        this.sisemeClient = sisemeClient;
        this.daumSearchClient = daumSearchClient;
        this.daumNewsRepository = daumNewsRepository;
    }

    @Override
    public List<SisemeResultModel> getSisemeResult(String regionName) {
        return sisemeClient.getRegionList(regionName)
                           .get()
                           .stream()
                           .map(x -> transform(x))
                           .collect(Collectors.toList());
    }

    @Override
    public List<DaumResultmodel> getDaumResult(String query) {
        return daumSearchClient.getNews(query, "recency")
                               .getDocuments()
                               .stream()
                               .map(x -> daumTransform(x))
                               .collect(Collectors.toList());
    }

    @Override
    public List<DaumNews> getDaumResult(SisemeResultModel sisemeResultModel) {
        return daumSearchClient.getNews(sisemeResultModel.getKeyword(), "recency")
                               .getDocuments()
                               .stream()
                               .map((x) -> {
                                   DaumNews daumNews = new DaumNews();
                                   daumNews.setType(sisemeResultModel.getType());
                                   daumNews.setCode(sisemeResultModel.getCode());
                                   daumNews.setKeyword(sisemeResultModel.getKeyword());
                                   daumNews.setTitle(x.getTitle());
                                   daumNews.setDatetime(x.getDatetime());
                                   daumNews.setUrl(x.getUrl());
                                   daumNews.setContents(x.getContents());
                                   return daumNews;
                               })
                               .collect(Collectors.toList());
    }

    @Override
    @Scheduled(cron="0 0 0/1 * * *")
    public List<NewsBySisemeModel> getNewsBySiseme() {
        List<SisemeResultModel> sisemeResultModelList = ListUtils.union(getSisemeResult("sido"), getSisemeResult("gungu"));
        List<DaumNews> batchResult = sisemeResultModelList.stream()
                                                                   .map(x -> getDaumResult(x))
                                                                   .flatMap(Collection::stream)
                                                                   .collect(Collectors.toList());
        daumNewsRepository.saveAll(batchResult);
        return batchResult.stream().map(x -> getNewsBySisemeTransform(x)).collect(Collectors.toList());
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

    private NewsBySisemeModel getNewsBySisemeTransform(DaumNews daumNews) {
        NewsBySisemeModel newsBySisemeModel = new NewsBySisemeModel();
        newsBySisemeModel.setType(daumNews.getType());
        newsBySisemeModel.setCode(daumNews.getCode());
        newsBySisemeModel.setKeyword(daumNews.getKeyword());
        newsBySisemeModel.setTitle(daumNews.getTitle());
        newsBySisemeModel.setDatetime(daumNews.getDatetime());
        newsBySisemeModel.setUrl(daumNews.getUrl());
        newsBySisemeModel.setContents(daumNews.getContents());
        return newsBySisemeModel;
    }

    private String changeRegionName(String regionName) {
        StringTokenizer st = new StringTokenizer(regionName);
        String keyword = st.nextToken();
        if (keyword.contains("특별")) {
            return keyword.substring(0,2)+'시';
        } else if(keyword.contains("광역")) {
            return keyword.substring(0,2)+'시';
        } else if(keyword.contains("남도") || keyword.contains("북도")) {
            return new StringBuilder().append(keyword.charAt(0)).append(keyword.charAt(2)).toString();
        }
        keyword = st.hasMoreTokens() ? keyword + " " + st.nextToken() : keyword;
        return keyword;
    }
}
