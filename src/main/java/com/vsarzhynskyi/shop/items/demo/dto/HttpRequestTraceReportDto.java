package com.vsarzhynskyi.shop.items.demo.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HttpRequestTraceReportDto {

    String method;
    String uri;
    String remoteAddress;
    int statusCode;
    long executionDurationMillis;

}
