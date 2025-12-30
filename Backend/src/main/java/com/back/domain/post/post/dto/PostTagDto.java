package com.back.domain.post.post.dto;

import com.back.domain.post.post.entity.PostTag;
import com.back.domain.post.tag.entity.Tag;

public record PostTagDto(
        String name
) {
    public PostTagDto(PostTag postTag){
        this(
                postTag.getTag().getName()
        );
    }
}
