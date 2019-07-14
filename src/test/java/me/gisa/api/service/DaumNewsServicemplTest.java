package me.gisa.api.service;

import com.google.common.collect.Lists;
import me.gisa.api.repository.entity.News;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DaumNewsServicemplTest {

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

    private Boolean isSameUrl(News priorNews, News searchNews) {
        return priorNews.getSubLink().equals(searchNews.getSubLink());
    }
}