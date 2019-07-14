package me.gisa.api.datatool.rss;

import me.gisa.api.datatool.common.LoggingFallbackFactory;
import me.gisa.api.datatool.rss.model.NewsfromRssResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RssClientFallbackFactory implements LoggingFallbackFactory<RssClient> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final RssClient FALLBACK = new RssClientFallbackFactory.RssClientFallback();

    @Override
    public RssClient fallback() {
        return FALLBACK;
    }

    @Override
    public Logger logger() {
        return logger;
    }

    public static class RssClientFallback implements RssClient {
        @Override
        public Optional<List<NewsfromRssResponse>> getNewsfromRssList(String regionName) {
            return Optional.empty();
        }
    }
}

