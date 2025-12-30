package com.back.domain.post.tag.entity;

import com.back.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Tag extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
