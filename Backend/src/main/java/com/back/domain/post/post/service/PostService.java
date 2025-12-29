package com.back.domain.post.post.service;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.repository.PostRepository;
import com.back.domain.post.postComment.entity.PostComment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Dictionary;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post write(String title, String content) {
        Post post = new Post(title, content);

        return postRepository.save(post);
    }

    public Optional<Post> findLatest() {
        return postRepository.findFirstByOrderByIdDesc();
    }

    public long count() {
        return postRepository.count();
    }

    public Post findById(long id) {
        return postRepository.findById(id).get();
    }

    public void modify(Post post, String title, String content) {
        post.update(title, content);
    }

    public void delete(long id) {
        postRepository.deleteById(id);
    }

    public PostComment writeComment(Post post, String comment) {
        return post.addComment(comment);
    }

    public void flush() {
        postRepository.flush();
    }

    public void modifyComment(PostComment postComment, String comment) {
        postComment.modify(comment);
    }

    public boolean deleteComment(Post post, PostComment postComment) {
        return post.deleteComment(postComment);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }
}
