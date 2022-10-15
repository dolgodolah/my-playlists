package com.myplaylists.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    private static final String LOG_ID = "logId";
    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, logId);
        request.setAttribute(START_TIME, System.currentTimeMillis());


        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            log.info("REQUEST [{}][{}][{}]", logId, requestURI, method.getName());
        } else {
            log.info("REQUEST [{}][{}][{}]", logId, requestURI, handler);
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
}
