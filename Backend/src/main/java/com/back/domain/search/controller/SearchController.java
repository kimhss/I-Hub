package com.back.domain.search.controller;

import com.back.domain.post.post.dto.PostDto;
import com.back.domain.search.dto.SearchCondition;
import com.back.domain.search.dto.SearchResponse;
import com.back.domain.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/search")
    public List<PostDto> search(@ModelAttribute SearchCondition condition) {
        return searchService.search(condition)
                .stream()
                .map(PostDto::new)
                .toList();
    }
}
