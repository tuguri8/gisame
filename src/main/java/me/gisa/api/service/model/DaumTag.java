package me.gisa.api.service.model;

public class DaumTag extends Tag<DaumTag> {

    public DaumTag() {
//        this.setNewsListTag(".newsList.top dl")
//            .setMultiLineUrlTag(".articleSubject a")
//            .setSingleUrlTag(".article_sponser a")
//            .setSummary(".articleSummary")
//            .setTitleTag(".article_info")
//            .setThumnailTag("#content img")
//            .setContentTag("#content")
//            .setDateTag(".article_sponsor > .article_date");
    }

    @Override
    protected DaumTag getThis() {
        return this;
    }


}
