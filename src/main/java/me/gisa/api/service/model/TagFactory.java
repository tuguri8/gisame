package me.gisa.api.service.model;

import me.gisa.api.repository.entity.NewsType;

public class TagFactory {

    public static Tag getTag(NewsType newsType){
        Tag tag = null;
        switch (newsType){
            case NAVER:
                tag = new NaverTag();
                break;
            case DAUM:
                tag = new DaumTag();
                break;
        }
        return tag;
    }

}
