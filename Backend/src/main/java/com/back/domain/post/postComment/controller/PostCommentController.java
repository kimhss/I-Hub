package com.back.domain.post.postComment.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import com.back.domain.post.postComment.dto.PostCommentDto;
import com.back.domain.post.postComment.entity.PostComment;
import com.back.global.rsData.RsData;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostCommentController {

    private final PostService postService;

    record postCommentReqBody(
            @NotBlank
            @Size(min = 2)
            String comment
    ){}

    @PostMapping("/post/{postId}/comment")
    @Transactional
    public RsData<PostCommentDto> write(@PathVariable long postId, @Valid @RequestBody postCommentReqBody request) {
        Post post = postService.findById(postId);
        PostComment postComment = postService.writeComment(post, request.comment);
        // 트랜잭션 끝난 후 수행되어야 하는 더티체킹 및 여러가지 작업들을 지금 당장 수행해라.
        postService.flush();
        return new RsData<>(
                "201-1",
                "%d번 댓글이 작성되었습니다.".formatted(postComment.getId()),
                new PostCommentDto(postComment)
        );
    }

    @PutMapping("/post/{postId}/comment/{id}")
    @Transactional
    public RsData<Void> modify(@PathVariable long postId, @PathVariable long id, @Valid @RequestBody postCommentReqBody request) {
        Post post = postService.findById(postId);
        PostComment postComment = post.findCommentById(id).get();
        postService.modifyComment(postComment, request.comment);

        return new RsData<>(
                "200-1",
                "%d번 댓글이 수정되었습니다.".formatted(postComment.getId()),
                null
        );
    }
}
