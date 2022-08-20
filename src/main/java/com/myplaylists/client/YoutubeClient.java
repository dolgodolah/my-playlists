package com.myplaylists.client;

import com.google.gson.Gson;
import com.myplaylists.dto.YoutubeDto;
import com.myplaylists.dto.YoutubeItem;
import com.myplaylists.dto.YoutubeResponse;
import com.myplaylists.exception.YoutubeApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
public class YoutubeClient {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();
    private static final Logger LOGGER = LoggerFactory.getLogger(YoutubeClient.class);
    private static final String YOUTUBE_API_URL = "https://www.googleapis.com/youtube/v3/search";
    private static final String YOUTUBE_API_QUERY_PARAMS = "?part=snippet&type=video&maxResults=5&videoEmbeddable=true";

    @Value("${youtube.api.key}")
    private String key;

    public YoutubeDto search(String q) {
        HttpResponse<String> response = requestSearch(q);
        return processResponse(response);
    }

    private HttpResponse<String> requestSearch(String q) {
        try {
            String uri = new StringBuilder(YOUTUBE_API_URL)
                    .append(YOUTUBE_API_QUERY_PARAMS)
                    .append("&key=").append(key)
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
            song.put("title", item.getSnippet().getTitle());
            song.put("videoId", item.getId().getVideoId());
            youtubeDto.add(song);
        }

        return youtubeDto;
    }
}
