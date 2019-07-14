package me.gisa.api.daum.datatool.daum;

import me.gisa.api.daum.datatool.daum.model.DaumSearchResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DaumClientTest {
    @Autowired
    DaumSearchClient daumSearchClient;

    @Test
    public void getNews() {
        DaumSearchResponse daumSearchResponse = daumSearchClient.getNews("서울 부동산", "recency");
        assertThat(daumSearchResponse).isNotNull();
    }
}
