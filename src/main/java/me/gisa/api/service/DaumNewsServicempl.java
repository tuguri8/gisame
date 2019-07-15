package me.gisa.api.service;

import me.gisa.api.datatool.daum.DaumSearchClient;
import me.gisa.api.datatool.daum.model.V1DaumSearchResponse;
import me.gisa.api.datatool.siseme.SisemeClient;
import me.gisa.api.datatool.siseme.model.Region;
import me.gisa.api.datatool.siseme.model.RegionGroup;
import me.gisa.api.datatool.siseme.model.RegionType;
import me.gisa.api.repository.entity.KeywordType;
import me.gisa.api.repository.entity.News;
import me.gisa.api.repository.entity.NewsRepository;
import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.service.model.SisemeResultModel;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Service
public class DaumNewsServicempl implements NewsService {
    private static final Logger log = LoggerFactory.getLogger(DaumNewsServicempl.class);

    private final SisemeClient sisemeClient;
    private final DaumSearchClient daumSearchClient;
    private final NewsRepository newsRepository;

    public DaumNewsServicempl(SisemeClient sisemeClient,
                              DaumSearchClient daumSearchClient,
                              NewsRepository newsRepository) {
        this.sisemeClient = sisemeClient;
        this.daumSearchClient = daumSearchClient;
        this.newsRepository = newsRepository;
    }

    // 시세미 API 검색 결과 리스트 반환
    private List getSisemeResult(RegionType regionType) {
        Optional<List<Region>> sisemeResult = sisemeClient.getRegionList(regionType.name());
        return sisemeResult.map(regions -> regions
            .stream()
            .map(this::transform)
            .collect(Collectors.toList())).orElse(Collections.EMPTY_LIST);
    }

    // 다음 검색 API 결과 리스트 반환
    private List<News> getDaumResult(SisemeResultModel sisemeResultModel) {
        return daumSearchClient.getNews(sisemeResultModel.getKeyword(), "recency")
                               .getDocuments()
                               .stream()
                               .map(documents -> transform(sisemeResultModel, documents))
                               .collect(Collectors.toList());
    }



    // 시세미 API 검색 결과로 다음 검색 DB 저장 스케쥴러
    @Override
    @Scheduled(cron = "* */2 * * * *")
    public void sync() {
        List<SisemeResultModel> sisemeResultModelList = ListUtils.union(getSisemeResult(RegionType.SIDO),
                                                     getSisemeResult(RegionType.GUNGU));
        List<News> priorResult = newsRepository.findAllByNewsType(NewsType.DAUM).orElse(Collections.EMPTY_LIST);
        List<News> searchResult = sisemeResultModelList.stream()
                                                      .map(this::getDaumResult)
                                                      .flatMap(Collection::stream)
                                                      .collect(Collectors.toList());
        // 검색된 결과에서 이전 DB 결과랑 비교하여 중복 결과를 제거
        searchResult.removeIf(newNews -> priorResult.stream().anyMatch(priorNews -> isSameUrl(priorNews, newNews)));
        newsRepository.saveAll(searchResult);
        log.info("시세미 <-> 다음 검색 API 배치 스케쥴러 작동, 추가된 뉴스 개수 :{}", searchResult.size());
    }

    private Boolean isSameUrl(News priorNews, News searchNews) {
        return priorNews.getSubLink().equals(searchNews.getSubLink());
    }

    private SisemeResultModel transform(Region region) {
        StringTokenizer st = new StringTokenizer(region.getFullName());
        String keyword = st.nextToken();
        keyword = st.hasMoreTokens() ? (RegionGroup.findByRegionName(keyword)
                                                   .getKeyword()+ " " + st.nextToken()) + "" + KeywordType.BOODONGSAN.getKeyword() : RegionGroup.findByRegionName(keyword)
                                                                                                                         .getKeyword() + "" + KeywordType.BOODONGSAN.getKeyword();
        SisemeResultModel sisemeResultModel = new SisemeResultModel();
        sisemeResultModel.setType(region.getType());
        sisemeResultModel.setCode(region.getCode());
        sisemeResultModel.setKeyword(keyword);
        sisemeResultModel.setSearchKeyword(KeywordType.BOODONGSAN);
        return sisemeResultModel;
    }

    private News transform(SisemeResultModel sisemeResultModel, V1DaumSearchResponse.Documents documents) {
        News news= new News();
        news.setTitle(documents.getTitle());
        news.setContent(documents.getContents());
        news.setSubLink(documents.getUrl());
        news.setPubDate(ZonedDateTime.parse(documents.getDatetime()).toLocalDate());
        news.setRegionCode(sisemeResultModel.getCode());
        news.setNewsType(NewsType.DAUM);
        news.setSearchKeyword(sisemeResultModel.getSearchKeyword());
        return news;
    }
}
