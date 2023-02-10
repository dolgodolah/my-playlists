package com.myplaylists.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    private static final String LOG_ID = "logId";
    private static final String START_TIME = "startTime";
    private static final String X_FORWARDED_FOR = "X-Forwarded-For";
    private static final List<String> IP_HEADER_NAMES = Arrays.asList("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP");
    private static final String UNKNOWN = "unknown";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, logId);
        request.setAttribute(START_TIME, System.currentTimeMillis());
        String remoteIp = getRemoteIp(request);

        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            log.info("REQUEST [{}][{}][{}][{}]", logId, remoteIp, requestURI, method.getName());
        } else {
            log.info("REQUEST [{}][{}][{}][{}]", logId, remoteIp, requestURI, handler);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);
        long start = (long) request.getAttribute(START_TIME);
        long responseTime = System.currentTimeMillis() - start;

        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            log.info("RESPONSE [{}][{}][{}][{}]", logId, requestURI, method.getName(), responseTime + "ms");
        } else {
            log.info("RESPONSE [{}][{}][{}][{}]", logId, requestURI, handler, responseTime + "ms");
        }
    }

    private String getRemoteIp(HttpServletRequest request) {
        return IP_HEADER_NAMES.stream()
                .map(name -> request.getHeader(name))
                .filter(header -> StringUtils.hasLength(header) && !UNKNOWN.equalsIgnoreCase(header))
                .findFirst()
                .orElse(request.getRemoteAddr());
    }
}
