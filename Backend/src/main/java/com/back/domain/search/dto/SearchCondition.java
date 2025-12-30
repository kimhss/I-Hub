package com.back.domain.search.dto;

import com.back.domain.search.enums.SearchTarget;
import com.back.domain.search.enums.SortType;
import lombok.*;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchCondition {
    // 검색어
    private String keyword;

    // 태그 필터
    private String tag;

    // 정렬 기준
    private SortType sort;

    // 검색 범위 확장용 (제목만 / 내용만 / 제목 + 내용)
    private SearchTarget target;

    public SortType getSort() {
        return sort == null ? SortType.LATEST : sort;
    }

    public SearchCondition(String keyword) {
        this(keyword,null, null, null);
    }
}
