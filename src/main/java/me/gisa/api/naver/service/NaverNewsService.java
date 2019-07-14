package me.gisa.api.naver.service;


import me.gisa.api.naver.controller.model.NewsResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NaverNewsService {

    List<NewsResponse> getNewsList(Pageable pageable);


}
