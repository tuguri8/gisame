package me.gisa.api.service.news.utils.summary;

import org.apache.commons.lang.StringUtils;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SummaryBuilder {

    private static int numOfSentences;
    private static double[][] intersectionMatrix;
    private static List<Sentence> sentences;

    public SummaryBuilder() {
        numOfSentences = 0;
        sentences = new ArrayList<>();
    }

    //주어진 본문을 문장단위로 split
    private void getSplitedSentences(String text) {

        BreakIterator breakIterator = BreakIterator.getSentenceInstance();
        breakIterator.setText(text);

        int idx = 0;
        while (breakIterator.next() != BreakIterator.DONE) {
            String splitSentence = text.substring(idx, breakIterator.current());
            sentences.add(new Sentence(splitSentence));
            numOfSentences += 1;
            idx = breakIterator.current();
        }
    }

    //두 문장 사이의 같은 단어 count
    private double numOfCommonWords(Sentence sentence1, Sentence sentence2) {

        double commonCount = 0;

        for (String str1Word : sentence1.getValue().split("\\s+")) {
            for (String str2Word : sentence2.getValue().split("\\s+")) {
                if (str1Word.compareToIgnoreCase(str2Word) == 0) {
                    commonCount++;
                }
            }
        }
        return commonCount;
    }

    //각 문장의 영향도 계산
    private void createIntersectionMatrix() {

        intersectionMatrix = new double[numOfSentences][numOfSentences];

        for (int i = 0; i < numOfSentences; i++) {

            for (int j = 0; j < numOfSentences; j++) {

                if (i <= j) {
                    Sentence str1 = sentences.get(i);
                    Sentence str2 = sentences.get(j);
                    intersectionMatrix[i][j] = numOfCommonWords(str1, str2) / ((double) (str1.getNumOfWords() + str2.getNumOfWords()) / 2);
                } else {
                    intersectionMatrix[i][j] = intersectionMatrix[j][i];
                }

            }
        }
    }

    //영향도를 계산한 문장을 Map에 저장
    private void createDictionary() {

        for (int i = 0; i < numOfSentences; i++) {

            double score = (i == 0) ? 1.0 : 0.0;

            for (int j = 0; j < numOfSentences; j++) {
                score += intersectionMatrix[i][j];
            }
            sentences.get(i).setScore(score);
        }
    }

    private void calcSummary(String text) {
        getSplitedSentences(text);
        createIntersectionMatrix();
        createDictionary();
        Collections.sort(sentences, (o1, o2) -> (int) (o2.getScore() - o1.getScore()));
    }

    public String getSummary(String text) {
        calcSummary(text);
        return sentences.stream().limit(3).map(Sentence::getValue).collect(Collectors.joining("."));
    }

}
