package me.gisa.api.datatool.naver;

import me.gisa.api.datatool.common.LoggingFallbackFactory;
import me.gisa.api.datatool.naver.model.v1.V1NaverNewsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class NaverClientFallbackFactory implements LoggingFallbackFactory<NaverClient> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final NaverClient FALLBACK = new NaverClientFallbackFactory().fallback();

    @Override
    public NaverClient fallback() {
        return FALLBACK;
    }

    @Override
    public Logger logger() {
        return logger;
    }

    public static class NaverClientFallback implements NaverClient {

        @Override
        public Optional<V1NaverNewsResponse> getNewsList(String query) {

            return Optional.empty();
        }
    }
}
