package me.gisa.api.service.news.utils.jsoup;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JsoupBuilder {

    private static final String newsTag = ".headline_list li";

    public static Optional<Document> getDocument(String url, Map<String, String> parameters) {
        Optional<Document> document = Optional.empty();
        try {
            document = Optional.ofNullable(Jsoup.connect(url)
                                                .userAgent(RandomUserAgent.getRandomUserAgent())
                                                .data(parameters)
                                                .execute()
                                                .parse());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static Optional<Document> getDocument(String url) {
        Optional<Document> document = Optional.empty();
        try {
            document = Optional.ofNullable(Jsoup.connect(url).userAgent(RandomUserAgent.getRandomUserAgent()).execute().parse());
        } catch (IOException e) {
            System.out.println("잘못된 URL이다.");
        }
        return document;
    }

    public static Map<String, String> getParamMap(String city_no, String page) {
        Map<String, String> parameters = new HashMap<String, String>();
        city_no = StringUtils.rightPad(city_no, 10, "0");
        parameters.put("city_no", city_no);
        parameters.put("page", page);
        return parameters;
    }
}
