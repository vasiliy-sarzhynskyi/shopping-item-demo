package com.vsarzhynskyi.shop.items.demo.converter;

import com.vsarzhynskyi.shop.items.demo.dto.PageableSearchResultDto;
import com.vsarzhynskyi.shop.items.demo.model.PageableSearchResult;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DefaultPageableSearchResultConverter implements PageableSearchResultConverter {

    @Override
    public <T, Y> PageableSearchResultDto<Y> toDto(PageableSearchResult<T> pageableSearchResult, Function<T, Y> convertFunction) {
        var convertedData = pageableSearchResult.getData().stream()
                .map(convertFunction::apply)
                .toList();

        return PageableSearchResultDto.<Y>builder()
                .data(convertedData)
                .totalPages(pageableSearchResult.getTotalPages())
                .totalElements(pageableSearchResult.getTotalElements())
                .build();
    }

}
