package com.back.domain.search.repository;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.entity.QPost;
import com.back.domain.search.dto.SearchCondition;
import com.back.domain.search.enums.SortType;
import com.querydsl.core.types.OrderSpecifier;
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

    @Override
    public List<Post> search(SearchCondition condition) {
        String keyword = "%" + condition.getKeyword() + "%";
        return queryFactory
                .selectFrom(post)
                .where(
                        post.title.like(keyword),
                        post.content.like(keyword)
                )
                .orderBy(sort(condition.getSort()))
                .fetch();
    }
}
