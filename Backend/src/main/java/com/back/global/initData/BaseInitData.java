package com.back.global.initData;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.entity.PostTag;
import com.back.domain.post.post.service.PostService;
import com.back.global.config.AppConfig;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    @Autowired
    @Lazy
    private BaseInitData self;
    private final PostService postService;
    private final MemberService memberService;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        if(postService.count() > 0) return;

        Post post1 = postService.write("제목 1", "내용 1", null);
        Post post2 = postService.write("제목 2", "내용 2", null);
        Post post3 = postService.write("제목 3", "내용 3", null);
        Post post4 = postService.write("검색 1", "검색 1", List.of("java", "spring-boot"));
        Post post5 = postService.write("검색 2", "본문 2", List.of("java"));

        post1.addComment("댓글 1-1");
        post1.addComment("댓글 1-2");
        post1.addComment("댓글 1-3");
        post2.addComment("댓글 2-1");
        post2.addComment("댓글 2-2");
    }

    @Transactional
    public void work2() {
        if (memberService.count() > 0) return;

        Member memberSystem = memberService.join("system", "1234", "시스템");
        if (AppConfig.isNotProd()) memberSystem.modifyApiKey(memberSystem.getUsername());

        Member memberAdmin = memberService.join("admin", "1234", "관리자");
        if (AppConfig.isNotProd()) memberAdmin.modifyApiKey(memberAdmin.getUsername());

        Member memberUser1 = memberService.join("user1", "1234", "유저1");
        if (AppConfig.isNotProd()) memberUser1.modifyApiKey(memberUser1.getUsername());

        Member memberUser2 = memberService.join("user2", "1234", "유저2");
        if (AppConfig.isNotProd()) memberUser2.modifyApiKey(memberUser2.getUsername());

        Member memberUser3 = memberService.join("user3", "1234", "유저3");
        if (AppConfig.isNotProd()) memberUser3.modifyApiKey(memberUser3.getUsername());
    }


}
