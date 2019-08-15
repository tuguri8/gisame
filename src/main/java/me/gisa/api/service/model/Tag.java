package me.gisa.api.service.model;

public abstract class Tag<T extends Tag<T>> {

    private String url;
    private String multiLineUrlTag;
    private String singleUrlTag;
    private String titleTag;
    private String contentTag;
    private String thumnailTag;
    private String dateTag;
    private String pressTag;
    private String newsListTag;
    private String summaryTag;

    protected abstract T getThis();

    public T setUrl(String url) {
        this.url = url;
        return getThis();
    }

    public T setMultiLineUrlTag(String multiLineUrlTag) {
        this.multiLineUrlTag = multiLineUrlTag;
        return getThis();
    }

    public T setTitleTag(String titleTag) {
        this.titleTag = titleTag;
        return getThis();
    }

    public T setContentTag(String contentTag) {
        this.contentTag = contentTag;
        return getThis();
    }

    public T setThumnailTag(String thumnailTag) {
        this.thumnailTag = thumnailTag;
        return getThis();
    }

    public T setDateTag(String dateTag) {
        this.dateTag = dateTag;
        return getThis();
    }

    public T setPressTag(String pressTag) {
        this.pressTag = pressTag;
        return getThis();
    }

    public T setNewsListTag(String newsListTag) {
        this.newsListTag = newsListTag;
        return getThis();
    }

    public T setSummary(String summaryTag) {
        this.summaryTag = summaryTag;
        return getThis();
    }

    public T setSingleUrlTag(String singleUrlTag) {
        this.singleUrlTag = singleUrlTag;
        return getThis();
    }

    public T setSummaryTag(String summaryTag) {
        this.summaryTag = summaryTag;
        return getThis();
    }

    public String getUrl() {
        return url;
    }

    public String getMultiLineUrlTag() {
        return multiLineUrlTag;
    }

    public String getTitleTag() {
        return titleTag;
    }

    public String getContentTag() {
        return contentTag;
    }

    public String getThumnailTag() {
        return thumnailTag;
    }

    public String getDateTag() {
        return dateTag;
    }

    public String getPressTag() {
        return pressTag;
    }

    public String getNewsListTag() {
        return newsListTag;
    }

    public String getSummaryTag() {
        return summaryTag;
    }

    public String getSingleUrlTag() {
        return singleUrlTag;
    }

    @Override
    public String toString() {
        return "Tag{" +
            "url='" + url + '\'' +
            ", multiLineUrlTag='" + multiLineUrlTag + '\'' +
            ", singleUrlTag='" + singleUrlTag + '\'' +
            ", titleTag='" + titleTag + '\'' +
            ", contentTag='" + contentTag + '\'' +
            ", thumnailTag='" + thumnailTag + '\'' +
            ", dateTag='" + dateTag + '\'' +
            ", pressTag='" + pressTag + '\'' +
            ", newsListTag='" + newsListTag + '\'' +
            ", summaryTag='" + summaryTag + '\'' +
            '}';
    }
}
