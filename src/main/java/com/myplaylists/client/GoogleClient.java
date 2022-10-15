package com.myplaylists.client;

import com.google.gson.Gson;
import com.myplaylists.dto.YoutubeDto;
import com.myplaylists.dto.YoutubeItem;
import com.myplaylists.dto.YoutubeResponse;
import com.myplaylists.dto.oauth.GoogleOauthDto;
import com.myplaylists.exception.ApiException;
import com.myplaylists.exception.YoutubeApiException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class GoogleClient {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();
    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleClient.class);
    private static final String YOUTUBE_SEARCH_API = "https://www.googleapis.com/youtube/v3/search";
    private static final String YOUTUBE_SEARCH_API_QUERY_PARAMS = "?part=snippet&type=video&maxResults=5&videoEmbeddable=true";
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
        HttpResponse<String> response = requestYoutubeSearch(q);
        return processResponse(response);
    }

    private HttpResponse<String> requestYoutubeSearch(String q) {
        try {
            String uri = new StringBuilder(YOUTUBE_SEARCH_API)
                    .append(YOUTUBE_SEARCH_API_QUERY_PARAMS)
                    .append("&key=").append(youtubeApikey)
                    .append("&q=").append(URLEncoder.encode(q, "UTF-8")).toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build();

            return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("youtube search keyword encoding error, q={}", q);
            throw new RuntimeException();
        } catch (IOException | InterruptedException e) {
            LOGGER.error("youtube api call error, q={}", q);
            throw new YoutubeApiException();
        }
    }

    private YoutubeDto processResponse(HttpResponse<String> response) {
        YoutubeResponse youtubeResponse = GSON.fromJson(response.body(), YoutubeResponse.class);

        YoutubeDto youtubeDto = new YoutubeDto();
        for (YoutubeItem item : youtubeResponse.getItems()) {
            if (item.getSnippet() == null || item.getId() == null) {
                LOGGER.warn("youtube api response is not valid, response={}", response.body());
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
            String uri = new StringBuilder(GOOGLE_USER_INFO_API)
                    .append("?access_token=").append(getAccessToken(code))
                    .toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return GSON.fromJson(response.body(), GoogleOauthDto.class);
        } catch (Exception e) {
            throw new ApiException("구글 로그인에 실패했습니다.", 500);
        }
    }

    private String getAccessToken(String code) throws IOException, InterruptedException {
        try {
            String params = new StringBuilder("grant_type=authorization_code")
                    .append("&client_id=" + googleApikey)
                    .append("&redirect_uri=" + REDIRECT_URI)
                    .append("&client_secret=" + googleClientSecret)
                    .append("&code=" + code)
                    .toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GOOGLE_ACCESS_TOKEN_API))
                    .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(params))
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return GSON.fromJson(response.body(), HashMap.class).get("access_token").toString();
        } catch (Exception e) {
            log.warn(GOOGLE_ACCESS_TOKEN_API + " api error");
            throw e;
        }

    }
}
