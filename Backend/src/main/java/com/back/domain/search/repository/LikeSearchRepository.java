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

    private BooleanExpression target(SearchTarget target, String keyword) {
        return switch (target) {
            case TITLE -> post.title.like(keyword);
            case BODY -> post.content.like(keyword);
            case TITLE_BODY -> post.title.like(keyword).or(post.content.like(keyword));
        };
    }

    @Override
    public List<Post> search(SearchCondition condition) {
        String keyword = "%" + condition.getKeyword() + "%";
        return queryFactory
                .selectFrom(post)
                .where(
                        target(condition.getTarget(), keyword)
                )
                .orderBy(sort(condition.getSort()))
                .fetch();
    }
}
