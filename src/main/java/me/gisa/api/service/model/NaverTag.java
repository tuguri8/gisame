package me.gisa.api.service.model;

public class NaverTag extends Tag<NaverTag> {

    public NaverTag() {
        this.setNewsList(".headline_list li")
                .setMultiLineUrlTag("dt")
                .setPortalUrl("a")
                .setSummary(".articleSummary")
                .setTitle(".article_header h3")
                .setThumbnail(".photo img")
                .setContent("#articleBody")
                .setDate("#news_write_date")
                .setPress("#news_writer");
    }

    @Override
    protected NaverTag getThis() {
        return this;
    }

}
