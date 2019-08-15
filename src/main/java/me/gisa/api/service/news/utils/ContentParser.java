package me.gisa.api.service.news.utils;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContentParser {

    private static final String EXCLUDE = "해당 언론사에서 선정하며 언론사 페이지(아웃링크)로 이동해 볼 수 있습니다.";

    public static String parseContent(String originalContent) {
        List<String> sentences = new ArrayList<>();
        BreakIterator breakIterator = BreakIterator.getSentenceInstance();
        breakIterator.setText(originalContent);

        int idx = 0;
        while (breakIterator.next() != BreakIterator.DONE) {
            String splitSentence = originalContent.substring(idx, breakIterator.current());
            sentences.add(splitSentence.trim());
            idx = breakIterator.current();
        }
        return sentences.stream().filter(sentence -> {
            return !sentence.contains(EXCLUDE);
        }).filter(sentence -> {
            return sentence.charAt(sentence.length() - 1) == '.';
        }).collect(Collectors.joining());
    }

}
