package me.gisa.api.service.news.utils.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class JsoupBuilder {

    public static Optional<Document> getDocument(String baseUrl, Map<String, String> parameters){
        Optional<Document> document = Optional.empty();
        try {
            document = Optional.ofNullable(Jsoup.connect(baseUrl)
                                                .userAgent(RandomUserAgent.getRandomUserAgent())
                                                .data(parameters)
                                                .execute()
                                                .parse());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static Optional<Document> getDocument(String baseUrl){
        Optional<Document> document = Optional.empty();
        try {
            document = Optional.ofNullable(Jsoup.connect(baseUrl)
                                                .userAgent(RandomUserAgent.getRandomUserAgent())
                                                .execute()
                                                .parse());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}
