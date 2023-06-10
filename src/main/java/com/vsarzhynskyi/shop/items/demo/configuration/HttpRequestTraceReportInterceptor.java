package com.vsarzhynskyi.shop.items.demo.configuration;

import com.vsarzhynskyi.shop.items.demo.dto.HttpRequestTraceReportDto;
import com.vsarzhynskyi.shop.items.demo.service.kafka.KafkaSender;
import com.vsarzhynskyi.shop.items.demo.service.time.TimeProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpRequestTraceReportInterceptor implements HandlerInterceptor {

    private static final String START_EXECUTION_TS_KEY = "startExecutionTimestamp";

    private final KafkaSender<HttpRequestTraceReportDto> httpRequestTraceReportKafkaSender;
    private final TimeProvider timeProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.debug("HTTP request preHandle with method = {}, URI = {}, IP = {}",
                request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
        request.setAttribute(START_EXECUTION_TS_KEY, timeProvider.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        var startTimestamp = (long) request.getAttribute(START_EXECUTION_TS_KEY);
        var endTimestamp = timeProvider.currentTimeMillis();
        var durationMillis = endTimestamp - startTimestamp;

        var statusCode = response.getStatus();
        var method = request.getMethod();
        var requestURI = request.getRequestURI();
        var remoteAddress = request.getRemoteAddr();

        log.debug("HTTP request afterCompletion with method = {}, URI = {}, IP = {}, duration = {} millis, status ode = {}",
                method, requestURI, remoteAddress, durationMillis, statusCode);

        var httpRequestTraceReportDto = HttpRequestTraceReportDto.builder()
                .method(method)
                .uri(requestURI)
                .remoteAddress(remoteAddress)
                .statusCode(statusCode)
                .executionDurationMillis(durationMillis)
                .build();
        httpRequestTraceReportKafkaSender.sendMessage(httpRequestTraceReportDto);
    }
}

