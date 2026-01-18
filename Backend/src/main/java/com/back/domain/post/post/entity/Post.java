package com.back.domain.post.post.entity;

import com.back.domain.member.member.entity.Member;
import com.back.domain.post.postComment.entity.PostComment;
import com.back.domain.post.tag.entity.Tag;
import com.back.global.entity.BaseEntity;
import com.back.global.exception.ServiceException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends BaseEntity {
    @ManyToOne
    private Member author;
    private String title;
    private String content;

    @OneToMany(mappedBy = "post", fetch = LAZY, cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = true)
    private List<PostTag> tags = new ArrayList<>();

    public Post(Member actor, String title, String content) {
        author = actor;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostComment addComment(Member actor, String comment) {
        PostComment postComment = new PostComment(actor, comment, this);
        comments.add(postComment);

        return postComment;
    }

    public void addTag(Tag tag) {
        PostTag postTag = new PostTag(this, tag);
        tags.add(postTag);
    }

    public Optional<PostComment> findCommentById(long id) {
        return comments.stream()
                .filter(comment -> comment.getId() == id)
                .findFirst();
    }

    public boolean deleteComment(PostComment postComment) {
        if (postComment == null) return false;

        return comments.remove(postComment);
    }

    public void checkActorCanModify(Member actor) {
        if (author.getId() != actor.getId())
            throw new ServiceException("403-1", "%d번 글 수정권한이 없습니다.".formatted(getId()));
    }

    public void checkActorCanDelete(Member actor) {
        if (author.getId() != actor.getId())
            throw new ServiceException("403-2", "%d번 글 삭제권한이 없습니다.".formatted(getId()));
    }
}
