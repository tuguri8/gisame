package me.gisa.api.datatool.siseme;

import com.google.common.collect.Lists;
import me.gisa.api.datatool.common.LoggingFallbackFactory;
import me.gisa.api.datatool.siseme.model.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SisemeClientFallbackFactory implements LoggingFallbackFactory<SisemeClient> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final SisemeClient FALLBACK = new SisemeClientFallbackFactory.SisemeClientFallback();

    @Override
    public SisemeClient fallback() {
        return FALLBACK;
    }

    @Override
    public Logger logger() {
        return logger;
    }

    public static class SisemeClientFallback implements SisemeClient {
        @Override
        public Optional<List<Region>> getRegionList(String regionType) {
            return Optional.empty();
        }
    }
}
