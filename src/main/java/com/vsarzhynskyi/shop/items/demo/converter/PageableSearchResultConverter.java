package com.vsarzhynskyi.shop.items.demo.converter;

import com.vsarzhynskyi.shop.items.demo.dto.PageableSearchResultDto;
import com.vsarzhynskyi.shop.items.demo.model.PageableSearchResult;

import java.util.function.Function;

public interface PageableSearchResultConverter {

    <T, Y> PageableSearchResultDto<Y> toDto(PageableSearchResult<T> pageableSearchResult, Function<T, Y> convertFunction);

}
