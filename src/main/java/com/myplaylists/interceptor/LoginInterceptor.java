package com.myplaylists.interceptor;

import com.myplaylists.exception.NotAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        if (session == null || session.getAttribute("user") == null) {
            log.info("not authorized user, request uri = {}", request.getRequestURI());
            throw new NotAuthorizedException("로그인을 먼저 진행해주세요.");
        }

        return true;
    }
}
