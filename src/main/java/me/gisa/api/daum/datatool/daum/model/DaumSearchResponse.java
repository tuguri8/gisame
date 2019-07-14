package me.gisa.api.daum.datatool.daum.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class DaumSearchResponse {
    private Meta meta;
    private List<Documents> documents;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Documents> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Documents> documents) {
        this.documents = documents;
    }

    public static class Meta {
        private Integer dup_count;
        private Integer pageable_count;
        private Integer total_count;
        private Boolean is_end;

        public Integer getDup_count() {
            return dup_count;
        }

        public void setDup_count(Integer dup_count) {
            this.dup_count = dup_count;
        }

        public Integer getPageable_count() {
            return pageable_count;
        }

        public void setPageable_count(Integer pageable_count) {
            this.pageable_count = pageable_count;
        }

        public Integer getTotal_count() {
            return total_count;
        }

        public void setTotal_count(Integer total_count) {
            this.total_count = total_count;
        }

        public Boolean getIs_end() {
            return is_end;
        }

        public void setIs_end(Boolean is_end) {
            this.is_end = is_end;
        }
    }
    public static class Documents {
        private String title;
        private String datetime;
        private String url;
        private String contents;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }
    }
}
