package me.gisa.api.service.model;

public class DaumTag extends Tag<DaumTag> {

    public DaumTag() {
        this.setNewsList(".list_partnews li")        //완료
            .setMultiLineUrlTag(".link_thumb")          //
            .setPortalUrl(".tit a")
            .setSummary(".articleSummary")
            .setTitle(".tit_account")
            .setThumbnail(".frame_thumb")
            .setContent(".wrap_newsbody")
            .setDate(".info .time")
            .setPress(".info .source");

    }

    @Override
    protected DaumTag getThis() {
        return this;
    }


}
