package com.back.domain.search.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.search.dto.SearchCondition;
import com.back.domain.search.enums.SearchTarget;
import com.back.domain.search.service.SearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SearchControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private SearchService searchService;

    @DisplayName("'제목' 검색")
    @Test
    void t1() throws Exception{
        ResultActions resultActions = mvc
                .perform(
                        get("/api/search").param("keyword", "제목")
                )
                .andDo(print());

        List<Post> posts = searchService.search(new SearchCondition("제목"));

        resultActions
                .andExpect(handler().handlerType(SearchController.class))
                .andExpect(handler().methodName("search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(posts.size()));
    }

    @DisplayName("'검색' 검색")
    @Test
    void t2() throws Exception{
        ResultActions resultActions = mvc
                .perform(
                        get("/api/search").param("keyword", "검색")
                )
                .andDo(print());

        List<Post> posts = searchService.search(new SearchCondition("검색"));

        resultActions
                .andExpect(handler().handlerType(SearchController.class))
                .andExpect(handler().methodName("search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(posts.size()));
    }

    @DisplayName("'검색' 제목 검색")
    @Test
    void t3() throws Exception{
        ResultActions resultActions = mvc
                .perform(
                        get("/api/search").param("keyword", "검색").param("target", "TITLE")
                )
                .andDo(print());

        List<Post> posts = searchService.search(new SearchCondition("검색", SearchTarget.TITLE));

        resultActions
                .andExpect(handler().handlerType(SearchController.class))
                .andExpect(handler().methodName("search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(posts.size()));
    }

    @DisplayName("'검색' 본문 검색")
    @Test
    void t4() throws Exception{
        ResultActions resultActions = mvc
                .perform(
                        get("/api/search").param("keyword", "검색").param("target", "BODY")
                )
                .andDo(print());

        List<Post> posts = searchService.search(new SearchCondition("검색", SearchTarget.BODY));

        resultActions
                .andExpect(handler().handlerType(SearchController.class))
                .andExpect(handler().methodName("search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(posts.size()));
    }
}
