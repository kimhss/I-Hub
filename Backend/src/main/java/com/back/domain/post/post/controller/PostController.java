package com.back.domain.post.post.controller;

import com.back.domain.post.post.dto.PostDto;
import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.entity.PostTag;
import com.back.domain.post.post.service.PostService;
import com.back.global.rsData.RsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    record PostWriteReqBody(
            @NotBlank
            @Size(min = 2, max = 100)
            String title,
            @NotBlank
            @Size(min = 2, max = 5000)
            String content,
            List<String> tags
    ) {
    }

    @PostMapping("/post")
    @Transactional
    RsData<PostDto> write(@Valid @RequestBody PostWriteReqBody request) {
        Post post = postService.write(request.title, request.content, request.tags);

        return new RsData<>(
                "201-1",
                "%d번 글이 작성되었습니다.".formatted(post.getId()),
                new PostDto(post)
        );
    }

    @PutMapping("/post/{id}")
    @Transactional
    RsData<Void> modify(@Valid @RequestBody PostWriteReqBody request, @PathVariable long id) {
        Post post = postService.findById(id);

        postService.modify(post, request.title, request.content);
        return new RsData<>(
                "200-1",
                "%d번 글이 수정되었습니다.".formatted(id)
        );
    }

    @DeleteMapping("/post/{id}")
    @Transactional
    RsData<Void> delete(@PathVariable long id) {
        postService.delete(id);
        return new RsData<>(
                "200-1",
                "%d번 글이 삭제되었습니다.".formatted(id)
        );
    }


    @GetMapping("/post/{id}")
    PostDto getItem(@PathVariable long id) {
        Post post = postService.findById(id);
        return new PostDto(post);
    }

    @GetMapping("/posts")
    List<PostDto> getItems() {
        List<Post> posts = postService.findAll();

        return posts.stream()
                .map(PostDto::new)
                .toList();
    }
}
