package me.gisa.api.service.news;

import me.gisa.api.datatool.siseme.SisemeClient;
import me.gisa.api.service.news.utils.summary.Sentence;
import me.gisa.api.service.news.utils.summary.SummaryBuilder;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openkoreantext.processor.tokenizer.KoreanSentenceSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NewsServiceImplTest {

    @Autowired
    SisemeClient sisemeClient;

    @Autowired
    ScrapService scrapService;

    @Autowired
    NewsServiceImpl newsService;

    @Test
    public void test() throws IOException, JAXBException {
        newsService.sync();
    }

    @Test
    public void summaryTest() {
        String content = "부정청탁 의심 70명 수사의뢰… 특별공급 자격자 추첨해 재분양\n" +
            "임신진단서를 위조하거나 위장전입을 하는 등 불법적으로 아파트 청약에 응모해 분양을 받은 당첨자들이 적발됐다. 부정행위로 계약이 취소된 주택은 해당 지역의 무주택 가구주나 특별공급 대상자에게 추첨 형태로 다시 분양된다. \n" +
            "\n" +
            "국토교통부는 2017, 2018년 분양한 전국 282개 단지의 신혼부부·다자녀 특별공급 당첨자 3297명을 전수 조사한 결과 부정청약 의심 사례 70명을 적발하고 수사 의뢰했다고 13일 밝혔다. 국토부에 따르면 이 중 " +
            "62명은 실제 출산, 유산 여부를 소명하지 못하는 등 허위로 임신진단서를 제출한 정황이 있는 것으로 밝혀졌다. 예를 들어 A 씨는 실제 자녀가 한 명인데 쌍둥이를 임신해 자녀가 3명이라고 속여 청약에 당첨됐다. 위장전입 등" +
            " 부정 청약을 한 사례 8명도 적발됐다. \n" +
            "\n" +
            "부정청약 사실이 확인되면 분양 계약이 취소되며, 3년 이하의 징역 또는 3000만 원 이하의 벌금형을 받을 수 있다. 위반행위 적발일로부터 최장 10년 동안 청약 신청이 제한된다. \n" +
            "\n" +
            "국토부는 부정행위로 계약이 취소된 주택을 재분양하는 방식도 14일부터 바뀐다고 밝혔다. 특별공급(신혼부부 등)으로 공급됐던 주택은 해당 지역에서 같은 특별공급 자격이 있는 사람을 대상으로 추첨해 공급한다. 일반공급으로 " +
            "공급됐다가 계약이 취소된 주택은 해당 지역의 무주택 가구주 가운데 추첨해 선정한다.";

        SummaryBuilder summaryBuilder = new SummaryBuilder();
        System.out.println(summaryBuilder.getSummary(content));
    }
}
