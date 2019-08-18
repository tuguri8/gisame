package me.gisa.api.service.model;

public abstract class Tag<T extends Tag<T>> {

    private String url;
    private String multiLineUrlTag;
    private String portalUrl;
    private String title;
    private String content;
    private String thumbnail;
    private String date;
    private String press;
    private String newsList;
    private String summary;

    protected abstract T getThis();

    public T setUrl(String url) {
        this.url = url;
        return getThis();
    }

    public T setMultiLineUrlTag(String multiLineUrlTag) {
        this.multiLineUrlTag = multiLineUrlTag;
        return getThis();
    }

    public T setTitle(String title) {
        this.title = title;
        return getThis();
    }

    public T setContent(String content) {
        this.content = content;
        return getThis();
    }

    public T setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return getThis();
    }

    public T setDate(String date) {
        this.date = date;
        return getThis();
    }

    public T setPress(String press) {
        this.press = press;
        return getThis();
    }

    public T setNewsList(String newsList) {
        this.newsList = newsList;
        return getThis();
    }

    public T setPortalUrl(String portalUrl) {
        this.portalUrl = portalUrl;
        return getThis();
    }

    public T setSummary(String summary) {
        this.summary = summary;
        return getThis();
    }

    public String getUrl() {
        return url;
    }

    public String getMultiLineUrlTag() {
        return multiLineUrlTag;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDate() {
        return date;
    }

    public String getPress() {
        return press;
    }

    public String getNewsList() {
        return newsList;
    }

    public String getSummary() {
        return summary;
    }

    public String getPortalUrl() {
        return portalUrl;
    }

    @Override
    public String toString() {
        return "Tag{" +
            "url='" + url + '\'' +
            ", multiLineUrlTag='" + multiLineUrlTag + '\'' +
            ", portalUrl='" + portalUrl + '\'' +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", thumbnail='" + thumbnail + '\'' +
            ", date='" + date + '\'' +
            ", press='" + press + '\'' +
            ", newsList='" + newsList + '\'' +
            ", summary='" + summary + '\'' +
            '}';
    }
}
