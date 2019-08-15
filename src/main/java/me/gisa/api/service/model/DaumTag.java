package me.gisa.api.service.model;

public class DaumTag extends Tag<DaumTag> {

    public DaumTag() {
        this.setNewsListTag(".list_partnews li")        //완료
            .setMultiLineUrlTag(".link_thumb")          //
            .setSingleUrlTag(".tit a")
            .setSummary(".articleSummary")
            .setTitleTag(".tit_account")
            .setThumnailTag(".frame_thumb")
            .setContentTag(".wrap_newsbody")
            .setDateTag(".info .time")
            .setPressTag(".info .source");

    }

    @Override
    protected DaumTag getThis() {
        return this;
    }


}
