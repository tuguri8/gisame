package me.gisa.api.service.gisame;

import me.gisa.api.common.utils.LocalDateParser;
import me.gisa.api.repository.entity.KeywordType;
import me.gisa.api.repository.entity.News;
import me.gisa.api.repository.entity.NewsRepository;
import me.gisa.api.repository.entity.NewsType;
import me.gisa.api.service.model.NewsModel;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GisameServicempl implements GisameService {

    private final NewsRepository newsRepository;
    private final LocalDateParser localDateParser;
    private final ModelMapper modelMapper;

    public GisameServicempl(NewsRepository newsRepository, LocalDateParser localDateParser, ModelMapper modelMapper) {
        this.newsRepository = newsRepository;
        this.localDateParser = localDateParser;
        this.modelMapper = modelMapper;
    }

    @Override
    // 저장된 DB에서 조건에 따라 뉴스를 가져온다, 없으면 빈 리스트
    public List<NewsModel> getNewsList(String regionCode, String searchKeyword, String newsType, String startDate, String endDate, Pageable pageable) {
        List<News> newsList = newsRepository.findAllByRegionCodeAndSearchKeywordAndNewsTypeAndPubDateBetween(regionCode,
                                                                                                             KeywordType.fromString(
                                                                                                                 searchKeyword),
                                                                                                             NewsType
                                                                                                                 .fromString(newsType),
                                                                                                             localDateParser.stringToLocalDate(
                                                                                                                 startDate),
                                                                                                             localDateParser.stringToLocalDate(
                                                                                                                 endDate),
                                                                                                             pageable)
                                            .orElse(Collections.emptyList());
        return newsList.stream()
                       .map(news -> modelMapper.map(news, NewsModel.class))
                       .collect(Collectors.toList());

    }
}
