package com.back.domain.search.repository;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.entity.QPost;
import com.back.domain.search.dto.SearchCondition;
import com.back.domain.search.enums.SearchTarget;
import com.back.domain.search.enums.SortType;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeSearchRepository implements SearchRepository {

    private final JPAQueryFactory queryFactory;
    private final QPost post = QPost.post;

    private OrderSpecifier<?> sort(SortType sort) {
        return switch (sort) {
            case LATEST -> post.createDate.desc();
            case OLDEST -> post.createDate.asc();
        };
    }

    private BooleanExpression target(SearchCondition condition) {
        String keyword = condition.getKeyword();
        SearchTarget target = condition.getTarget();

        if (!StringUtils.hasText(keyword)) {
            return null; // ⭐ 핵심
        }

        String likeKeyword = "%" + keyword + "%";

        if (target == null) {
            return post.title.like(likeKeyword)
                    .or(post.content.like(likeKeyword));
        }

        return switch (target) {
            case TITLE -> post.title.like(likeKeyword);
            case BODY -> post.content.like(likeKeyword);
            case TITLE_BODY -> post.title.like(likeKeyword).or(post.content.like(likeKeyword));
        };
    }


    private BooleanExpression tag(String tag) {
        if (!StringUtils.hasText(tag)) return null;

        return post.tags.any().tag.name.eq(tag);
    }

    @Override
    public List<Post> search(SearchCondition condition) {
        return queryFactory
                .selectFrom(post)
                .where(
                        target(condition),
                        tag(condition.getTag())
                )
                .orderBy(sort(condition.getSort()))
                .fetch();
    }
}
