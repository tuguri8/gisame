package me.gisa.api.common.utils;

import me.gisa.api.repository.NewsRepository;
import me.gisa.api.repository.entity.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openkoreantext.processor.KoreanPosJava;
import org.openkoreantext.processor.KoreanTokenJava;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.tokenizer.KoreanSentenceSplitter;
import org.openkoreantext.processor.tokenizer.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import scala.collection.Seq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NewsSummarizer {
    private static final Logger log = LoggerFactory.getLogger(NewsSummarizer.class);
    private final NewsRepository newsRepository;

    public NewsSummarizer(NewsRepository newsRepository) {this.newsRepository = newsRepository;}

    // 뉴스를 요약 후, DB에 저장한다
    @Async
    public void summarizeNews(News news) {

        try {
            Document doc = Jsoup.connect(news.getOriginalLink()).execute().parse();
            news.setContent(doc.select(".wrap_newsbody").text());
        } catch (IOException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }

        List<String> splittedSentences = splitParagraph(news.getContent());
        List<List<String>> taggedSentenceList = splittedSentences.stream()
                                                                 .map(this::tagging)
                                                                 .collect(Collectors.toList());
        List<String> keywordList = getKeywordListFromNews(taggedSentenceList);

        HashMap<Integer, Double> tfIdfMap = new HashMap<>();
        // [['나','부동산],[...],[...]]
        for (List<String> sentenceTags : taggedSentenceList) { // sentenceTags = [나, 부동산]
            Double resulTfIdf = taggedSentenceList.indexOf(sentenceTags) == 0 ? 0.8 : 0.0; // 첫 문장에는 0.8의 가중치 부여
            for (String keyword : keywordList) { // keyword : 부동산
                Long tf = getTf(sentenceTags, keyword);
                Double idf = getIdf(taggedSentenceList, keyword);
                Double tfIdf = tf * idf;
                resulTfIdf += tfIdf;
            }
            tfIdfMap.put(taggedSentenceList.indexOf(sentenceTags), resulTfIdf);
        }

        // Map 내림차순 정렬
        Map<Integer, Double> sortedMap = tfIdfMap.entrySet().stream()
                                                 .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                                 .collect(Collectors.toMap(Map.Entry::getKey,
                                                                           Map.Entry::getValue,
                                                                           (e1, e2) -> e1,
                                                                           LinkedHashMap::new));

        List<Integer> sortedIndexSentence = new ArrayList<>(sortedMap.keySet());
        String summarizedNews = sortedIndexSentence.size() > 2 ? String.format("%s %s %s",
                                                                               splittedSentences.get(sortedIndexSentence.get(0)),
                                                                               splittedSentences.get(sortedIndexSentence.get(1)),
                                                                               splittedSentences.get(sortedIndexSentence.get(2)))
            : String.format(splittedSentences.toString());
        news.setSummary(summarizedNews);
        newsRepository.save(news);
        log.info(news.getTitle() + " 저장완료");
    }

    // 뉴스 기사에서 TF-IDF 상위 5개를 키워드로 반환한다
    private List<String> getKeywordListFromNews(List<List<String>> taggedSentenceList) {
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

        // Map 내림차순 정렬
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

    // TF 계산
    private Long getTf(List<String> sentence, String keyword) {
        return sentence.stream().filter(word -> word.equals(keyword)).count();
    }

    // IDF 계산
    private Double getIdf(List<List<String>> tagList, String keyword) {
        int df = 1;
        df += tagList.stream().filter(sentence -> sentence.stream().anyMatch(word -> word.equals(keyword))).count();
        return Math.log10((double) tagList.size() / df);
    }

    // 문장을 받아서 2글자 이상의 명사 or 동사만 분석해서 리스트로 반환한다
    private List<String> tagging(String text) {
        Seq tokens = OpenKoreanTextProcessorJava.tokenize(text);
        List<KoreanTokenJava> tokenList = OpenKoreanTextProcessorJava.tokensToJavaKoreanTokenList(tokens);
        return tokenList.stream()
                        .filter(token -> (token.getPos().equals(KoreanPosJava.Noun) || token.getPos()
                                                                                            .equals(KoreanPosJava.Verb)) && token.getLength() > 1)
                        .map(KoreanTokenJava::getText)
                        .collect(Collectors.toList());
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
