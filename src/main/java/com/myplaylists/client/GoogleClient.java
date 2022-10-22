package com.myplaylists.client;

import com.google.gson.Gson;
import com.myplaylists.dto.YoutubeDto;
import com.myplaylists.dto.YoutubeItem;
import com.myplaylists.dto.YoutubeResponse;
import com.myplaylists.dto.oauth.GoogleOauthDto;
import com.myplaylists.exception.ApiException;
import com.myplaylists.exception.YoutubeApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class GoogleClient {
    private static final Gson GSON = new Gson();
    private static final String YOUTUBE_SEARCH_API = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&maxResults=5&videoEmbeddable=true";
    private static final String GOOGLE_ACCESS_TOKEN_API = "https://oauth2.googleapis.com/token";
    private static final String GOOGLE_USER_INFO_API = "https://www.googleapis.com/oauth2/v2/userinfo";
    private static final String REDIRECT_URI = "http://localhost:3000/login/google";

    @Value("${youtube.api.key}")
    private String youtubeApikey;

    @Value("${google.api.key}")
    private String googleApikey;

    @Value("${google.client.secret}")
    private String googleClientSecret;

    public YoutubeDto searchYoutube(String q) {
        try {
            Map<String, Object> queryParams = Map.of("key", youtubeApikey, "q", URLEncoder.encode(q, "UTF-8"));

            String response = AsyncHttpClient.getWithQueryParams(YOUTUBE_SEARCH_API, queryParams);
            return processResponse(response);
        } catch (UnsupportedEncodingException e) {
            log.error("failed to encode, q={}", q);
            throw new RuntimeException();
        } catch (Exception e) {
            log.error("failed to request {}, q = {}", YOUTUBE_SEARCH_API, q);
            throw new YoutubeApiException("유튜브 검색에 실패했습니다.");
        }
    }

    private YoutubeDto processResponse(String response) {
        YoutubeResponse youtubeResponse = GSON.fromJson(response, YoutubeResponse.class);

        YoutubeDto youtubeDto = new YoutubeDto();
        for (YoutubeItem item : youtubeResponse.getItems()) {
            if (item.getSnippet() == null || item.getId() == null) {
                log.warn("youtube api response is not valid, response={}", response);
                continue;
            }

            Map<String, Object> song = new HashMap<>();
            song.put("title", HtmlUtils.htmlUnescape(item.getSnippet().getTitle()));
            song.put("videoId", item.getId().getVideoId());
            youtubeDto.add(song);
        }

        return youtubeDto;
    }

    public GoogleOauthDto getGoogleUserInfo(String code) {
        try {
            String response = AsyncHttpClient.getWithQueryParams(GOOGLE_USER_INFO_API, Map.of("access_token", getAccessToken(code)));
            return GSON.fromJson(response, GoogleOauthDto.class);
        } catch (Exception e) {
            log.error("failed to request {}", GOOGLE_USER_INFO_API);
            throw new ApiException("구글 로그인에 실패했습니다.", 500);
        }
    }

    private String getAccessToken(String code) {
        try {
            String data = new StringBuilder()
                    .append("grant_type=").append("authorization_code")
                    .append("&client_id=").append(googleApikey)
                    .append("&redirect_uri=").append(REDIRECT_URI)
                    .append("&client_secret=").append(googleClientSecret)
                    .append("&code=").append(code)
                    .toString();

            String response = AsyncHttpClient.post(GOOGLE_ACCESS_TOKEN_API, data, Map.of(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded"));
            return GSON.fromJson(response, HashMap.class).get("access_token").toString();
        } catch (Exception e) {
            log.error("failed to request {}", GOOGLE_ACCESS_TOKEN_API);
            throw new ApiException("구글 액세스 토큰 발급을 실패했습니다.", 500);
        }

    }
}
