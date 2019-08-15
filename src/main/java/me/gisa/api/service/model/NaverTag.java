package me.gisa.api.service.model;

public class NaverTag extends Tag<NaverTag> {

    public NaverTag() {
        this.setNewsListTag(".headline_list li")
                .setMultiLineUrlTag("dt")
                .setSingleUrlTag("a")
                .setSummary(".articleSummary")
                .setTitleTag(".article_header h3")
                .setThumnailTag(".photo img")
                .setContentTag("#articleBody")
                .setDateTag("#news_write_date")
                .setPressTag("#news_writer")
                .setExcludeTag(".link_news");
    }

    @Override
    protected NaverTag getThis() {
        return this;
    }

}
