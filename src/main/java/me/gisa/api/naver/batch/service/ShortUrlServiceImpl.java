package me.gisa.api.naver.batch.service;

import me.gisa.api.common.utils.ShortUrlBuilder;
import me.gisa.api.naver.repository.NewsRepository;
import me.gisa.api.naver.repository.entity.News;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private final static Integer MAX_LENGTH = 8;
    private final ShortUrlBuilder shortUrlBuilder;
    private final NewsRepository newsRepository;

    public ShortUrlServiceImpl(ShortUrlBuilder shortUrlBuilder, NewsRepository newsRepository) {
        this.shortUrlBuilder = shortUrlBuilder;
        this.newsRepository = newsRepository;
    }

    @Override
    public String createShortUrl(String path, String webUrl) {
        String shortPath = StringUtils.isEmpty(path) ? shortUrlBuilder.shorten(MAX_LENGTH) : path;
        if (existsPath(shortPath)) {
            if (StringUtils.isEmpty(path)) {
//                throw new UrlShorteningFailureException.PathIsAlreadyInUseException();
            }
//            throw new UrlShorteningFailureException.CustomPathIsAlreadyInUseException();
        }
        return shortPath;
    }

    private Boolean existsPath(String path) {
        News news = newsRepository.findByPathAndActive(path, Boolean.TRUE);
        return Objects.isNull(news) ? Boolean.FALSE : news.getActive();
    }
}
