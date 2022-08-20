package com.myplaylists.client;

import com.google.gson.Gson;
import com.myplaylists.dto.YoutubeDto;
import com.myplaylists.dto.YoutubeItem;
import com.myplaylists.dto.YoutubeResponse;
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

    @Value("${youtube.api.key}")
    private String key;

    public YoutubeDto search(String q) {
        YoutubeDto youtubeDto = new YoutubeDto();

        try {
            String uri = new StringBuilder(YOUTUBE_API_URL)
                    .append("?key=").append(key)
                    .append("&part=snippet&type=video&maxResults=5&videoEmbeddable=true")
                    .append("&q=").append(URLEncoder.encode(q, "UTF-8")).toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .GET()
                    .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            YoutubeResponse youtubeResponse = GSON.fromJson(response.body(), YoutubeResponse.class);

            for (YoutubeItem item : youtubeResponse.getItems()) {
                Map<String, Object> song = new HashMap<>();
                song.put("title", replaceTitle(item.getSnippet().getTitle()));
                song.put("videoId", item.getId().getVideoId());
                youtubeDto.add(song);
            }

        } catch (UnsupportedEncodingException e) {
            LOGGER.error("youtube search keyword encoding error");
        } catch (IOException | InterruptedException e) {
            LOGGER.error("youtube api call error q={}", q);
        }

        return youtubeDto;
    }

    private String replaceTitle(String title) {
        title = title.replaceAll("&#39;", "");
        title = title.replaceAll("&lt;", "<");
        title = title.replaceAll("&gt;", ">");
        title = title.replaceAll("&quot;", "");
        title = title.replaceAll("/", "");
        title = title.replaceAll("\\]", "");
        title = title.replaceAll("\\[", "");
        title = title.replaceAll("&amp;", "");
        return title;
    }
}
