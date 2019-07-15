package me.gisa.api.service.gisame;

import me.gisa.api.service.model.NewsModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GisameService {
    List<NewsModel> getNewsList(String regionCode, String searchKeyword, String newsType, String startDate, String endDate, Pageable pageable);
}
