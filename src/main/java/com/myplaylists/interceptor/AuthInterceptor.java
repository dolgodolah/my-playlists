package com.myplaylists.interceptor;

import com.myplaylists.exception.AuthRequiredException;
import com.myplaylists.exception.NotAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private static final Set<String> ALLOWED_GUEST_PAGES = new HashSet<>(Arrays.asList(null, "0", "1"));
    private static final Set<String> PLAYLISTS_URL = new HashSet<>(Arrays.asList("/playlists", "/playlists/"));
    private static final String ALL_PLAYLISTS_URL = "/all-playlists";
    private static final String SONGS_URL = "/songs";
    private static final String GET_METHOD = "GET";
    private static final String PAGE = "page";
    private static final Set<String> LOGIN_REQUIRED_PAGES = new HashSet<>(Arrays.asList("/me", "/playlists/add"));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        if (isAllowedRequest(request)) {
            return true;
        }

        if (session == null || session.getAttribute("user") == null) {
            log.info("not authorized user, request uri = {}", request.getRequestURI());

            if (LOGIN_REQUIRED_PAGES.contains(request.getRequestURI())) {
                throw new AuthRequiredException();
            }

            throw new NotAuthorizedException();
        }

        return true;
    }

    private boolean isAllowedRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        String pageParameter = request.getParameter(PAGE);

        // 플레이리스트 뷰
        if (PLAYLISTS_URL.equals(requestURI) && GET_METHOD.equals(requestMethod)) {
            return true;
        }

        // 모든 플레이리스트 조회 API
        if (ALL_PLAYLISTS_URL.equals(requestURI)
                && GET_METHOD.equals(requestMethod)
                && ALLOWED_GUEST_PAGES.contains(pageParameter)
        ) {
            return true;
        }

        // 수록곡 조회 API
        if (SONGS_URL.equals(requestURI) && GET_METHOD.equals(requestMethod)) {
            return true;
        }

        return false;
    }
}
