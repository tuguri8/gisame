package me.gisa.api.service.model;

public class NaverTag extends Tag<NaverTag> {

    public NaverTag() {
        this.setNewsListTag(".headline_list li")
                .setMultiLineUrlTag("dt")
                .setSingleUrlTag("a")
                .setSummary(".articleSummary")
                .setTitleTag("")
                .setThumnailTag(".end_photo_org img")
                .setContentTag("#articleBody")
                .setDateTag("#news_write_date")
                .setPressTag("#news_writer");
    }

    @Override
    protected NaverTag getThis() {
        return this;
    }

}
