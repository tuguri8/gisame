package me.gisa.api.naver.service;

import com.google.common.collect.Lists;
import me.gisa.api.naver.repository.NewsRepository;
import me.gisa.api.naver.controller.model.NewsResponse;
import me.gisa.api.naver.repository.entity.News;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NaverNewsServiceImpl implements NaverNewsService {

    private final NewsRepository naverNewsRepository;
    private final ModelMapper modelMapper;

    public NaverNewsServiceImpl(NewsRepository naverNewsRepository, ModelMapper modelMapper) {
        this.naverNewsRepository = naverNewsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<NewsResponse> getNewsList(Pageable pageable) {

        return naverNewsRepository.findAllByOrderByPubDateDesc(pageable).stream().map(news -> transform(news)).collect(Collectors.toList());
    }

    private NewsResponse transform(News news) {
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setTitle(news.getTitle());
        newsResponse.setContent(news.getContent());
        newsResponse.setPath(news.getPath());
        newsResponse.setWebUrl(news.getWebUrl());
        newsResponse.setPubDate(news.getPubDate());
        return newsResponse;
    }

//    @Override
//    public List<NewsResponse> getNewsList(Pageable pageable) {
//        return naverNewsRepository.findAllByOrderByPubDateDesc(pageable)
//                                  .map(news -> modelMapper.map(news, NewsResponse.class))
//                                  .stream()
//                                  .collect(
//                                      Collectors.toList());
//    }

}
