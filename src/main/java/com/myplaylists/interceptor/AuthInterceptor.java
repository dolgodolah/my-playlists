package com.myplaylists.interceptor;

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

    private static final Set<String> ALLOWED_GUEST_PAGES = new HashSet<>(Arrays.asList("0", "1"));
    private static final String ALL_PLAYLIST_PATH = "/all-playlists";
    private static final String SONGS_PATH = "/songs";
    private static final String GET = "GET";
    private static final String PAGE = "page";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        if (isGuestRequest(request)) {
            return true;
        }

        if (session == null || session.getAttribute("user") == null) {
            log.info("not authorized user, request uri = {}", request.getRequestURI());
            throw new NotAuthorizedException();
        }

        return true;
    }

    private boolean isGuestRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        String pageParameter = request.getParameter(PAGE);

        if (GET.equals(requestMethod) && SONGS_PATH.equals(requestURI)) {
            return true;
        }

        if (ALL_PLAYLIST_PATH.equals(requestURI) && ALLOWED_GUEST_PAGES.contains(pageParameter)) {
            return true;
        }

        return false;
    }
}
