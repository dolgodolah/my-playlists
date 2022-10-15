package com.myplaylists.interceptor;

import com.myplaylists.exception.NotAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        if (session == null || session.getAttribute("user") == null) {
            log.info("not authorized user, request uri = {}", request.getRequestURI());
            throw new NotAuthorizedException();
        }

        return true;
    }
}
