package com.back.domain.search.service;

import com.back.domain.post.post.dto.PostDto;
import com.back.domain.post.post.entity.Post;
import com.back.domain.search.dto.SearchCondition;
import com.back.domain.search.enums.SearchTarget;
import com.back.domain.search.enums.SortType;
import com.back.domain.search.repository.LikeSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final LikeSearchRepository likeSearchRepository;

    public List<Post> search(SearchCondition condition) {
        return likeSearchRepository.search(normalize(condition));
    }

    public SearchCondition normalize(SearchCondition condition) {
        if (condition.getSort() == null) {
            condition.setSort(SortType.LATEST);
        }
        if (condition.getTarget() == null) {
            condition.setTarget(SearchTarget.TITLE_BODY);
        }
//        if (condition.getSize() <= 0 || condition.getSize() > 50) {
//            condition.setSize(20);
//        }
        return condition;
    }

}
