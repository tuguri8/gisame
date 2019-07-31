package me.gisa.api.service;

import me.gisa.api.datatool.siseme.SisemeClient;
import me.gisa.api.datatool.siseme.model.Region;
import me.gisa.api.datatool.siseme.model.RegionGroup;
import me.gisa.api.datatool.siseme.model.RegionType;
import me.gisa.api.repository.entity.KeywordType;
import me.gisa.api.repository.entity.News;
import me.gisa.api.service.model.SisemeResultModel;
import org.apache.commons.collections4.ListUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DaumNewsServicemplTest {

    @Autowired
    private SisemeClient sisemeClient;

    private static final Logger log = LoggerFactory.getLogger(DaumNewsServicemplTest.class);

    @Test
    public void 중복_데이터_제거() {
        News news1 = new News();
        news1.setTitle("1");
        news1.setSubLink("abcd");
        News news2 = new News();
        news2.setTitle("2");
        news2.setSubLink("abcd");
        News news3 = new News();
        news3.setTitle("3");
        news3.setSubLink("abcdf");
        News news4 = new News();
        news4.setTitle("4");
        news4.setSubLink("abcdfg");
        List<News> priorResult = new ArrayList<News>();
        priorResult.addAll(Arrays.asList(news1, news4));
        List<News> searchResult = new ArrayList<News>();
        searchResult.addAll(Arrays.asList(news1, news3));
        searchResult.removeIf(newNews -> priorResult.stream().anyMatch(priorNews -> isSameUrl(priorNews, newNews)));
        log.info(searchResult.get(0).toString());
    }

    private List getSisemeResult(RegionType regionType) {
        Optional<List<Region>> sisemeResult = sisemeClient.getRegionList(regionType.name());
        return sisemeResult.map(regions -> regions
            .stream()
            .map(Region::getFullName)
            .collect(Collectors.toList())).orElse(Collections.EMPTY_LIST);
    }

    private String getKeyword(String name) {
        StringTokenizer st = new StringTokenizer(name);
        String keyword = st.nextToken();
        keyword = st.hasMoreTokens() ? (RegionGroup.findByRegionName(keyword)
                                                   .getKeyword() + " " + st.nextToken()) + " " + KeywordType.BOODONGSAN.getKeyword() :
            RegionGroup
                .findByRegionName(keyword)
                .getKeyword() + " " + KeywordType.BOODONGSAN.getKeyword();
        return keyword;
    }

    @Test
    public void 시세미_Enum_null_체크_테스트() {
        List sisemeResultModelList = ListUtils.union(getSisemeResult(RegionType.SIDO),
                                                     getSisemeResult(RegionType.GUNGU));
        List<String> keywordList = (List<String>) sisemeResultModelList.stream().map(fullName -> getKeyword((String) fullName)).collect(Collectors.toList());
        log.info(keywordList.toString());
    }


    private Boolean isSameUrl(News priorNews, News searchNews) {
        return priorNews.getSubLink().equals(searchNews.getSubLink());
    }
}