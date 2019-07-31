package me.gisa.api.naver.repository.entity;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import me.gisa.api.repository.entity.News;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QNews is a Querydsl query type for News
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNews extends EntityPathBase<News> {

    private static final long serialVersionUID = -1803054316L;

    public static final QNews news = new QNews("news");

    public final me.gisa.api.naver.repository.entity.common.QBaseEntity _super = new me.gisa.api.naver.repository.entity.common.QBaseEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.util.Date> createdDateTime = _super.createdDateTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDateTime = _super.lastModifiedDateTime;

    public final StringPath path = createString("path");

    public final DatePath<java.time.LocalDate> pubDate = createDate("pubDate", java.time.LocalDate.class);

    public final StringPath title = createString("title");

    public final StringPath webUrl = createString("webUrl");

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

