package me.gisa.api.naver.service;

import me.gisa.api.datatool.naver.NaverClient;
import me.gisa.api.datatool.naver.NaverClientProperties;
import me.gisa.api.datatool.naver.model.NaverNewsItems;
import me.gisa.api.datatool.naver.model.NaverNewsResponse;
import me.gisa.api.datatool.sisemi.SisemeClient;
import me.gisa.api.datatool.sisemi.model.Region;
import me.gisa.api.naver.repository.NaverNewsRepository;
import me.gisa.api.naver.repository.entity.NaverNews;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

@Service
@Configuration
@EnableConfigurationProperties({NaverClientProperties.class})
public class NaverNewsServiceImpl implements NaverNewsService {

    private final NaverClientProperties naverClientProperties;
    private final NaverNewsRepository naverNewsRepository;
    private final SisemeClient sisemeClient;
    private final NaverClient naverClient;

    public NaverNewsServiceImpl(NaverClientProperties naverClientProperties,
                                NaverNewsRepository naverNewsRepository,
                                SisemeClient sisemeClient,
                                NaverClient naverClient) {
        this.naverClientProperties = naverClientProperties;
        this.naverNewsRepository = naverNewsRepository;
        this.sisemeClient = sisemeClient;
        this.naverClient = naverClient;
    }

    @Override
    public List<NaverNews> getNewsList() {
        return naverNewsRepository.findByIdGreaterThan(0L);
    }

    @Override
    public void syncNewsList() {
        List<String> keywords = getKeywordList(getRegionList("gungu"));
        //검색 키워드 하나만 테스트.
        for (String keyword : keywords) {
            Optional<NaverNewsResponse> naverNewsResponse = naverClient.getNewsList(naverClientProperties.getClientId(),
                                                                                    naverClientProperties.getClientSecret(),
                                                                                    keyword);
            for (NaverNewsItems item : naverNewsResponse.get().getItems()) {
                naverNewsRepository.save(transform(item));
            }
        }

    }

    private NaverNews transform(NaverNewsItems naverNewsItems) {
        NaverNews naverNews = new NaverNews();
        naverNews.setTitle(naverNewsItems.getTitle());
        naverNews.setContent(naverNewsItems.getDescription());
        naverNews.setWebUrl(naverNewsItems.getOriginallink());
        naverNews.setPath("ShortUrl 미구현");
        return naverNews;
    }

    private List<String> getKeywordList(Optional<List<Region>> optionalRegions) {
        List<String> keywords = new ArrayList<>();
        optionalRegions.get().forEach(region -> {
            String fullName = region.getFullName();
            StringTokenizer st = new StringTokenizer(fullName, " ");
            String sido = st.nextToken();
            String gungu = "";
            if (st.hasMoreTokens()) { gungu = st.nextToken(); }

            if (sido.contains("특별")) {
                sido = sido.substring(0, sido.indexOf("특별"));
            } else if (sido.contains("광역")) {
                sido = sido.substring(0, sido.indexOf("광역"));
            } else if (sido.contains("북도")) {
                sido = sido.charAt(0) + "북";
            } else if (sido.contains("남도")) {
                sido = sido.charAt(0) + "남";
            }
            keywords.add(sido + " " + gungu + " 부동산");
        });
        return keywords;
    }

    private Optional<List<Region>> getRegionList(String regionType) {
        return sisemeClient.getRegionList(regionType);
    }
}
