package com.back.domain.post.postComment.controller;

import com.back.domain.member.member.entity.Member;
import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import com.back.domain.post.postComment.dto.PostCommentDto;
import com.back.domain.post.postComment.entity.PostComment;
import com.back.global.rq.Rq;
import com.back.global.rsData.RsData;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post/{postId}/comments")
public class PostCommentController {

    private final PostService postService;
    private final Rq rq;

    record postCommentReqBody(
            @NotBlank
            @Size(min = 2)
            String comment
    ){}

    @PostMapping
    @Transactional
    public RsData<PostCommentDto> write(@PathVariable long postId, @Valid @RequestBody postCommentReqBody request) {
        Member actor = rq.getActor();
        Post post = postService.findById(postId);
        PostComment postComment = postService.writeComment(actor, post, request.comment);
        // 트랜잭션 끝난 후 수행되어야 하는 더티체킹 및 여러가지 작업들을 지금 당장 수행해라.
        postService.flush();
        return new RsData<>(
                "201-1",
                "%d번 댓글이 작성되었습니다.".formatted(postComment.getId()),
                new PostCommentDto(postComment)
        );
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData<Void> modify(@PathVariable long postId, @PathVariable long id, @Valid @RequestBody postCommentReqBody request) {
        Member actor = rq.getActor();
        Post post = postService.findById(postId);
        PostComment postComment = post.findCommentById(id).get();
        postComment.checkActorCanModify(actor);

        postService.modifyComment(postComment, request.comment);

        return new RsData<>(
                "200-1",
                "%d번 댓글이 수정되었습니다.".formatted(postComment.getId())
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    public RsData<Void> delete(@PathVariable long postId, @PathVariable long id) {
        Member actor = rq.getActor();
        Post post = postService.findById(postId);
        PostComment postComment = post.findCommentById(id).get();
        postComment.checkActorCanDelete(actor);

        postService.deleteComment(post, postComment);

        return new RsData<>(
                "200-1",
                "%d번 댓글이 삭제되었습니다.".formatted(id)
        );
    }

    @GetMapping("/{id}")
    public PostCommentDto getItem(@PathVariable long postId, @PathVariable long id) {
        Post post = postService.findById(postId);
        PostComment postComment = post.findCommentById(id).get();

        return new PostCommentDto(postComment);
    }

    @GetMapping
    public List<PostCommentDto> getItems(@PathVariable long postId) {
        Post post = postService.findById(postId);
        return post.getComments()
                .stream()
                .map(PostCommentDto::new)
                .toList();
    }
}
