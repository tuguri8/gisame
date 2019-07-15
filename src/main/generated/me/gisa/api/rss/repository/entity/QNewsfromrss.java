package me.gisa.api.rss.repository.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNewsfromrss is a Querydsl query type for Newsfromrss
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNewsfromrss extends EntityPathBase<Newsfromrss> {

    private static final long serialVersionUID = -2001646698L;

    public static final QNewsfromrss newsfromrss = new QNewsfromrss("newsfromrss");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath link = createString("link");

    public final StringPath pubDate = createString("pubDate");

    public final StringPath regionName = createString("regionName");

    public final StringPath title = createString("title");

    public QNewsfromrss(String variable) {
        super(Newsfromrss.class, forVariable(variable));
    }

    public QNewsfromrss(Path<? extends Newsfromrss> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNewsfromrss(PathMetadata metadata) {
        super(Newsfromrss.class, metadata);
    }

}

