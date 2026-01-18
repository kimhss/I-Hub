package com.back.domain.post.postComment.entity;

import com.back.domain.member.member.entity.Member;
import com.back.global.entity.BaseEntity;
import com.back.domain.post.post.entity.Post;
import com.back.global.exception.ServiceException;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PostComment extends BaseEntity {
    @ManyToOne
    private Member author;
    private String comment;
    @ManyToOne()
    private Post post;

    public PostComment(Member actor, String comment, Post post) {
        author = actor;
        this.comment = comment;
        this.post = post;
    }

    public void modify(String comment) {
        this.comment = comment;
    }

    public void checkActorCanModify(Member actor) {
        if(author.getId() != actor.getId()) {
            throw new ServiceException("403-1", "%d번 댓글 수정권한이 없습니다.".formatted(getId()));
        }
    }

    public void checkActorCanDelete(Member actor) {
        if(author.getId() != actor.getId()) {
            throw new ServiceException("403-1", "%d번 댓글 삭제권한이 없습니다.".formatted(getId()));
        }
    }
}
