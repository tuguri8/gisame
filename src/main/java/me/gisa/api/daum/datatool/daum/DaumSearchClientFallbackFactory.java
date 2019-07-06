package me.gisa.api.daum.datatool.daum;

import me.gisa.api.daum.datatool.common.LoggingFallbackFactory;
import me.gisa.api.daum.datatool.daum.model.DaumSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DaumSearchClientFallbackFactory implements LoggingFallbackFactory<DaumSearchClient> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final DaumSearchClient FALLBACK = new DaumSearchClientFallbackFactory.DaumSearchClientFallback();

    @Override
    public DaumSearchClient fallback() {
        return FALLBACK;
    }

    @Override
    public Logger logger() {
        return logger;
    }

    public static class DaumSearchClientFallback implements DaumSearchClient {
        @Override
        public DaumSearchResponse getNews(String query, String sort) {
            return null;
        }
    }
}
