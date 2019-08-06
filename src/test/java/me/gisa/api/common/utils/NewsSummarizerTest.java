package me.gisa.api.common.utils;

import org.junit.Test;
import org.openkoreantext.processor.KoreanPosJava;
import org.openkoreantext.processor.KoreanTokenJava;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.tokenizer.KoreanSentenceSplitter;
import org.openkoreantext.processor.tokenizer.Sentence;
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
    private final String EXAMPLE_NEWS = "LH(한국토지주택공사)에서 첫 여성 부사장이 배출됐다. LH는 5일 신임 부사장에 첫 여성 임원 출신인 장옥선 상임이사(53·사진)를 선임했다. LH는 장 부사장이 지난해 상임이사로 임용된 뒤 경영혁신본부장, 기획재무본부장을 거치면서 적극적인 업무추진 등으로 경영진으로부터 역량을 인정받았다고 설명했다. 강원 출신으로 강원대 경영학과를 졸업하고 1988년 LH에 입사해 주거복지처장, 도시계획처장, 산업단지처장, 경영관리실장 등 공사 요직을 두루 거쳤다. 일자리 창출 및 경영혁신 업무를 담당할 경영혁신본부장(상임이사)에는 서창원 법무실장이, 3기 신도시 업무추진을 담당할 스마트도시본부장(상임이사)에는 한병홍 도시재생본부장이 임명됐다. 변창흠 LH 사장은 “부사장과 상임이사 선임을 계기로 모든 임직원이 힘을 합쳐 주거복지로드맵, 도시재생뉴딜, 3기 신도시 등 정부 정책 수행에 더욱 매진할 것”이라고 밝혔다. 배정철 기자 bjc@hankyung.com ▶ 네이버에서 한국경제 뉴스를 받아보세요 ▶ 한경닷컴 바로가기 ▶ 모바일한경 구독신청 ⓒ 한국경제 & hankyung.com, 무단전재 및 재배포 금지";

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
        List<String> splittedSentences = splitParagraph(EXAMPLE_NEWS);
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
        List<String> splittedSentences = splitParagraph(EXAMPLE_NEWS);
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
        List<String> splittedSentences = splitParagraph(EXAMPLE_NEWS);
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
        List<String> sentences = splitParagraph(EXAMPLE_NEWS);
        for (String sentence : sentences) {
            log.info(sentence);
        }
    }

    // 문단을 OKT를 이용하여 문장을 나눈 후, Seq을 Java의 List로 변환하여 반환한다
    private List<String> splitParagraph(String newsText) {
        List<Sentence> splittedSentences = scala.collection.JavaConversions.seqAsJavaList(KoreanSentenceSplitter.split(newsText));
        return splittedSentences.stream()
                                .map(Sentence::text)
                                .filter(sentence -> sentence.substring(sentence.length() - 1).equals("."))
                                .collect(Collectors.toList());
    }

}