package com.vsarzhynskyi.shop.items.demo.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PageableSearchResult<T> {
    private Integer totalPages;
    private Long totalElements;
    private List<T> data;
}
