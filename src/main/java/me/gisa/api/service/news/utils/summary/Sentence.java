package me.gisa.api.service.news.utils.summary;

public class Sentence {

    private String value;
    private double numOfWords;
    private double score;

    public Sentence(String value) {
        this.value = value;
        this.numOfWords = this.getWordCount();
    }

    public Sentence(String value, int numOfWords, double score) {
        this.value = value;
        this.numOfWords = numOfWords;
        this.score = score;
    }

    private double getWordCount() {
        double wordCount = 0.0;
        wordCount += (value.split(" ")).length;
        return wordCount;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getNumOfWords() {
        return numOfWords;
    }

    public void setNumOfWords(double numOfWords) {
        this.numOfWords = numOfWords;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
