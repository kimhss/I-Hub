package com.back.domain.search.repository;

import com.back.domain.post.post.entity.Post;
import com.back.domain.search.dto.SearchCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository {
    List<Post> search(SearchCondition condition);
}
