package com.back.domain.post.post.dto;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.entity.PostTag;

import java.time.LocalDateTime;
import java.util.List;

public record PostDto(
        long id,
        String title,
        String content,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        List<PostTag> tags
) {
    public PostDto(Post post) {
        this(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreateDate(),
                post.getModifyDate(),
                post.getTags()
        );
    }
}
