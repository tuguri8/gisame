package me.gisa.api.common.utils;

import kr.bydelta.koala.hnn.SentenceSplitter;
import org.junit.Test;
import org.openkoreantext.processor.KoreanPosJava;
import org.openkoreantext.processor.KoreanTokenJava;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.collection.Seq;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NewsSummarizerTest {
    private static final Logger log = LoggerFactory.getLogger(NewsSummarizerTest.class);
    private final String EXAMPLE_NEWS = "고양 창릉 신도시 전경./서울경제DB [서울경제] 고양 창릉, 부천 대장 등 추가 3기 신도시가 발표된 지 3개월 가량 흐른 가운데 일산신도시 집값 하락 폭이 더 커진 " +
        "것으로 나타났다. 신도시 발표 전 " +
        "5개월보다 발표 후 3개월간 아파트값이 더 떨어진 것이다. 눈길을 끄는 것은 신도시 발표 후 서울 강남 3구는 플러스 변동률로 돌아섰고, 수도권의 웬만한 지역도 낙폭을 줄였다는 점이다. 일산만 최근 집값 상승 분위기에서 " +
        "벗어난 것이다. 앞서 국토교통부는 지난 5월 7일 3기 신도시 입지를 확정해 발표했다. 이런 가운데 향후 일산 집값 전망에 대해서는 의견이 엇갈리고 있다. ◇ 더 떨어진 일산 집값 = 5일 본지가 한국감정원 자료를 분석한 " +
        "결과 지난 5월 7일 3기 신도시가 발표된 이후 현재까지 경기도 고양시 아파트값은 1.31% 하락했다. 신도시 발표 전 5개월간 낙폭(-1.25%)을 넘어선 수치다. 고양시 집값 낙폭이 커진 이유는 일산신도시 때문이다. " +
        "발표 전 5개월과 발표 후 3개월 집값 변동률을 보면 일산 신도시가 위치한 서구는 -1.56%에서 -1.62%로 낙폭이 커졌다. 동구도 -1.03%에서 -1.30%로 짧은 기간 하락세가 강해졌다. 고양 창릉이 위치한 " +
        "덕양구만이 -1.10%에서 -1.03%로 소폭 내림세가 완화했다. 실제 일산서구 주엽동 문촌마을11단지 LIG건영 전용 84㎡는 지난해 11월 3억 9,500만원에서 지난달 말 3억 4,500만원으로 떨어졌다. 일산동 " +
        "일산산들마을5단지 전용 84㎡도 4월 평균 매매가는 2억 9,200만원을 기록했는데 7월 말에는 2억 5,500만원으로 하락한 상태다. 일산 아파트값 침체는 다른 지역과는 사뭇 다른 모양새다. 전국 기준으로도 지난 석 달 " +
        "동안 -0.73% 하락해 올 초 다섯 달간 1.52% 떨어진 데서 하락 폭이 절반으로 축소했다. 경기 전체 아파트값도 -1.50%에서 -0.66%로 하락세가 둔화한 상태다. 3기 신도시 추가 지정에 악영향을 받았던 " +
        "검단신도시 일대도 이 기간 상승 전환했다. 인천 서구는 신도시 이전 -0.49% 하락했다가 최근 상승 기조가 붙어 3개월 간 0.02% 아파트값이 올랐다. 부천 대장신도시 전경./서울경제DB ◇ 일산 집값 전망은 엇갈려 =" +
        " 경기권 지역도 아파트값 상승전환이 늘어났다. 고양 창릉과 함께 3기 신도시에 추가된 부천은 올 초 5월 전까지 -0.23%를 기록했지만 발표 후에는 0.08%가 올랐다. 서울 강남 3구는 일제히 플러스 변동률을 기록했다." +
        " 서초구는 신도시 발표 전 5개월간 -2.24%의 변동률을 기록했는 데 발표 후 3개월 동안에는 0.11% 올랐다. 강남구는 -2.73%에서 0.29%, 송파구는 -1.76%에서 0.13%의 변동률을 보였다. 일산 집값의 " +
        "침체가 언제 끝날 지에 대해서는 전망이 엇갈린다. 함영진 직방 빅데이터랩장은 “공급 확대가 공급 과잉이 될 것이라는 우려와 함께 상대적으로 서울과 가까운 곳에서 분양이 이뤄지면서 일산의 수요가 이탈 중”이라며 “일산신도시 " +
        "정비사업에 대한 계획이 나오지 않는 한 하락세가 더 길게 갈 수 있다”고 말했다. 박원갑 KB국민은행 부동산수석전문위원은 “사라진 기대 심리가 일산 매매시장에 가장 큰 변동요인”이라면서 “헌 아파트에 대한 가격 하한선에 " +
        "다다르면 하락 폭은 멈출 것”이라고 말했다./이재명기자 nowlight@sedaily.com";

    @Test
    public void 형태소_분석_테스트() {
        String text = "나는 부동산과 부동산을 많이 좋아한다.";
        Seq tokens = OpenKoreanTextProcessorJava.tokenize(text);
        List<KoreanTokenJava> tokenList = OpenKoreanTextProcessorJava.tokensToJavaKoreanTokenList(tokens);
        List<String> result = tokenList.stream()
                                       .filter(token -> token.getPos().equals(KoreanPosJava.Noun) || token.getPos()
                                                                                                          .equals(KoreanPosJava.Verb))
                                       .map(KoreanTokenJava::getText)
                                       .collect(Collectors.toList());
        log.info(result.toString());
    }

    @Test
    public void 키워드_추출_테스트() {
        SentenceSplitter splitter = new SentenceSplitter();
        List<String> splittedSentences = splitter.sentences(EXAMPLE_NEWS);
        List<List<String>> taggedSentenceList = splittedSentences.stream()
                                                                 .map(this::tagging)
                                                                 .collect(Collectors.toList());

        HashMap<String, Double> tfIdfMap = new HashMap<>();
        // [['나','부동산],[...],[...]]
        for (List<String> sentenceTags : taggedSentenceList) { // sentenceTags = [나, 부동산]
            for (String keyword : sentenceTags) { // keyword : 부동산
                Long tf = getTf(sentenceTags, keyword);
                Double idf = getIdf(taggedSentenceList, keyword);
                Double tfIdf = tf * idf;
                if (tfIdfMap.containsKey(keyword)) {
                    if (tfIdfMap.get(keyword).compareTo(tfIdf) > 0) { continue; }
                }
                tfIdfMap.put(keyword, tf * idf);
            }
        }

        Map<String, Double> sortedMap = tfIdfMap.entrySet().stream()
                                                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                                .collect(Collectors.toMap(Map.Entry::getKey,
                                                                          Map.Entry::getValue,
                                                                          (e1, e2) -> e1,
                                                                          LinkedHashMap::new));

        List<String> result = sortedMap.keySet()
                                       .stream()
                                       .limit(5)
                                       .collect(Collectors.toList());

        log.info(result.toString());
    }

    @Test
    public void 최종_요약() {
        SentenceSplitter splitter = new SentenceSplitter();
        List<String> splittedSentences = splitter.sentences(EXAMPLE_NEWS);
        List<List<String>> taggedSentenceList = splittedSentences.stream()
                                                      .map(this::tagging)
                                                      .collect(Collectors.toList());
        List<String> keywordList = getKeywordListFromNews();

        HashMap<Integer, Double> tfIdfMap = new HashMap<>();
        // [['나','부동산],[...],[...]]
        for (List<String> sentenceTags : taggedSentenceList) { // sentenceTags = [나, 부동산]
            Double resulTfIdf = 0.0;
            for (String keyword : keywordList) { // keyword : 부동산
                Long tf = getTf(sentenceTags, keyword);
                Double idf = getIdf(taggedSentenceList, keyword);
                Double tfIdf = tf * idf;
                resulTfIdf += tfIdf;
            }
            tfIdfMap.put(taggedSentenceList.indexOf(sentenceTags), resulTfIdf);
        }

        Map<Integer, Double> sortedMap = tfIdfMap.entrySet().stream()
                                                 .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                                 .collect(Collectors.toMap(Map.Entry::getKey,
                                                                           Map.Entry::getValue,
                                                                           (e1, e2) -> e1,
                                                                           LinkedHashMap::new));

        List<Integer> sortedIndexSentence = new ArrayList<>(sortedMap.keySet());
        log.info(splittedSentences.get(sortedIndexSentence.get(0)));
        log.info(splittedSentences.get(sortedIndexSentence.get(1)));
        log.info(splittedSentences.get(sortedIndexSentence.get(2)));
    }

    private List<String> getKeywordListFromNews() {
        SentenceSplitter splitter = new SentenceSplitter();
        List<String> splittedSentences = splitter.sentences(EXAMPLE_NEWS);
        List<List<String>> taggedSentenceList = splittedSentences.stream()
                                                                 .map(this::tagging)
                                                                 .collect(Collectors.toList());

        HashMap<String, Double> tfIdfMap = new HashMap<>();
        // [['나','부동산],[...],[...]]
        for (List<String> sentenceTags : taggedSentenceList) { // sentenceTags = [나, 부동산]
            for (String keyword : sentenceTags) { // keyword : 부동산
                Long tf = getTf(sentenceTags, keyword);
                Double idf = getIdf(taggedSentenceList, keyword);
                Double tfIdf = tf * idf;
                if (tfIdfMap.containsKey(keyword)) {
                    if (tfIdfMap.get(keyword).compareTo(tfIdf) > 0) { continue; }
                }
                tfIdfMap.put(keyword, tf * idf);
            }
        }

        Map<String, Double> sortedMap = tfIdfMap.entrySet().stream()
                                                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                                .collect(Collectors.toMap(Map.Entry::getKey,
                                                                          Map.Entry::getValue,
                                                                          (e1, e2) -> e1,
                                                                          LinkedHashMap::new));

        return sortedMap.keySet()
                        .stream()
                        .limit(5)
                        .collect(Collectors.toList());
    }

    private Long getTf(List<String> sentence, String keyword) {
        return sentence.stream().filter(word -> word.equals(keyword)).count();
    }

    private Double getIdf(List<List<String>> tagList, String keyword) {
        int df = 1;
        df += tagList.stream().filter(sentence -> sentence.stream().anyMatch(word -> word.equals(keyword))).count();
        return Math.log10((double) tagList.size() / df);
    }

    private List<String> tagging(String text) {
        Seq tokens = OpenKoreanTextProcessorJava.tokenize(text);
        List<KoreanTokenJava> tokenList = OpenKoreanTextProcessorJava.tokensToJavaKoreanTokenList(tokens);
        return tokenList.stream()
                        .filter(token -> (token.getPos().equals(KoreanPosJava.Noun) || token.getPos()
                                                                                            .equals(KoreanPosJava.Verb)) && token.getLength() > 1)
                        .map(KoreanTokenJava::getText)
                        .collect(Collectors.toList());
    }

    @Test
    public void 문장_분리_테스트() {
        String text = "고양 창릉 신도시 전경./서울경제DB [서울경제] 고양 창릉, 부천 대장 등 추가 3기 신도시가 발표된 지 3개월 가량 흐른 가운데 일산신도시 집값 하락 폭이 더 커진 것으로 나타났다. 신도시 발표 전 " +
            "5개월보다 발표 후 3개월간 아파트값이 더 떨어진 것이다. 눈길을 끄는 것은 신도시 발표 후 서울 강남 3구는 플러스 변동률로 돌아섰고, 수도권의 웬만한 지역도 낙폭을 줄였다는 점이다. 일산만 최근 집값 상승 분위기에서 " +
            "벗어난 것이다. 앞서 국토교통부는 지난 5월 7일 3기 신도시 입지를 확정해 발표했다. 이런 가운데 향후 일산 집값 전망에 대해서는 의견이 엇갈리고 있다. ◇ 더 떨어진 일산 집값 = 5일 본지가 한국감정원 자료를 분석한 " +
            "결과 지난 5월 7일 3기 신도시가 발표된 이후 현재까지 경기도 고양시 아파트값은 1.31% 하락했다. 신도시 발표 전 5개월간 낙폭(-1.25%)을 넘어선 수치다. 고양시 집값 낙폭이 커진 이유는 일산신도시 때문이다. " +
            "발표 전 5개월과 발표 후 3개월 집값 변동률을 보면 일산 신도시가 위치한 서구는 -1.56%에서 -1.62%로 낙폭이 커졌다. 동구도 -1.03%에서 -1.30%로 짧은 기간 하락세가 강해졌다. 고양 창릉이 위치한 " +
            "덕양구만이 -1.10%에서 -1.03%로 소폭 내림세가 완화했다. 실제 일산서구 주엽동 문촌마을11단지 LIG건영 전용 84㎡는 지난해 11월 3억 9,500만원에서 지난달 말 3억 4,500만원으로 떨어졌다. 일산동 " +
            "일산산들마을5단지 전용 84㎡도 4월 평균 매매가는 2억 9,200만원을 기록했는데 7월 말에는 2억 5,500만원으로 하락한 상태다. 일산 아파트값 침체는 다른 지역과는 사뭇 다른 모양새다. 전국 기준으로도 지난 석 달 " +
            "동안 -0.73% 하락해 올 초 다섯 달간 1.52% 떨어진 데서 하락 폭이 절반으로 축소했다. 경기 전체 아파트값도 -1.50%에서 -0.66%로 하락세가 둔화한 상태다. 3기 신도시 추가 지정에 악영향을 받았던 " +
            "검단신도시 일대도 이 기간 상승 전환했다. 인천 서구는 신도시 이전 -0.49% 하락했다가 최근 상승 기조가 붙어 3개월 간 0.02% 아파트값이 올랐다. 부천 대장신도시 전경./서울경제DB ◇ 일산 집값 전망은 엇갈려 =" +
            " 경기권 지역도 아파트값 상승전환이 늘어났다. 고양 창릉과 함께 3기 신도시에 추가된 부천은 올 초 5월 전까지 -0.23%를 기록했지만 발표 후에는 0.08%가 올랐다. 서울 강남 3구는 일제히 플러스 변동률을 기록했다." +
            " 서초구는 신도시 발표 전 5개월간 -2.24%의 변동률을 기록했는 데 발표 후 3개월 동안에는 0.11% 올랐다. 강남구는 -2.73%에서 0.29%, 송파구는 -1.76%에서 0.13%의 변동률을 보였다. 일산 집값의 " +
            "침체가 언제 끝날 지에 대해서는 전망이 엇갈린다. 함영진 직방 빅데이터랩장은 “공급 확대가 공급 과잉이 될 것이라는 우려와 함께 상대적으로 서울과 가까운 곳에서 분양이 이뤄지면서 일산의 수요가 이탈 중”이라며 “일산신도시 " +
            "정비사업에 대한 계획이 나오지 않는 한 하락세가 더 길게 갈 수 있다”고 말했다. 박원갑 KB국민은행 부동산수석전문위원은 “사라진 기대 심리가 일산 매매시장에 가장 큰 변동요인”이라면서 “헌 아파트에 대한 가격 하한선에 " +
            "다다르면 하락 폭은 멈출 것”이라고 말했다./이재명기자 nowlight@sedaily.com";
        SentenceSplitter splitter = new SentenceSplitter();
        List<String> splittedSentences = splitter.sentences(text);
        for (String s : splittedSentences) {
            log.info(s);
        }
    }

}