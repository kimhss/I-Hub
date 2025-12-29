package com.back.domain.post.postComment.entity;

import com.back.global.entity.BaseEntity;
import com.back.domain.post.post.entity.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PostComment extends BaseEntity {
    private String comment;
    @ManyToOne()
    private Post post;

    public PostComment(String comment, Post post) {
        this.comment = comment;
        this.post = post;
    }
}
