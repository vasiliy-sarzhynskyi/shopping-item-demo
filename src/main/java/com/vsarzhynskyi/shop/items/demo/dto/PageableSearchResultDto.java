package com.vsarzhynskyi.shop.items.demo.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PageableSearchResultDto<T> {
    private Integer totalPages;
    private Long totalElements;
    private List<T> data;
}
