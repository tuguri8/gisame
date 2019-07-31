package me.gisa.api.repository.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNews is a Querydsl query type for News
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNews extends EntityPathBase<News> {

    private static final long serialVersionUID = 431825270L;

    public static final QNews news = new QNews("news");

    public final me.gisa.api.repository.entity.common.QBaseEntity _super = new me.gisa.api.repository.entity.common.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.util.Date> createdDateTime = _super.createdDateTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDateTime = _super.lastModifiedDateTime;

    public final EnumPath<NewsType> newsType = createEnum("newsType", NewsType.class);

    public final StringPath originalLink = createString("originalLink");

    public final DatePath<java.time.LocalDate> pubDate = createDate("pubDate", java.time.LocalDate.class);

    public final StringPath regionCode = createString("regionCode");

    public final EnumPath<KeywordType> searchKeyword = createEnum("searchKeyword", KeywordType.class);

    public final StringPath subLink = createString("subLink");

    public final StringPath summary = createString("summary");

    public final StringPath title = createString("title");

    public QNews(String variable) {
        super(News.class, forVariable(variable));
    }

    public QNews(Path<? extends News> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNews(PathMetadata metadata) {
        super(News.class, metadata);
    }

}

