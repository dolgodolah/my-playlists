package com.myplaylists.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myplaylists.dto.YoutubeDto;
import com.myplaylists.exception.YoutubeApiException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.myplaylists.dto.YoutubeForm;

@Service
public class YoutubeService {
	@Value("${youtube.api.key}")
	private String apiKey;

	private static String API_URL = "https://www.googleapis.com/youtube/v3/search";

	// TODO: 유튜브 api 리팩토링
	public YoutubeDto search(String keyword) {
		try {
			API_URL += "?key=" + apiKey;
			API_URL += "&part=snippet&type=video&maxResults=5&videoEmbeddable=true";
			API_URL += "&q="+URLEncoder.encode(keyword,"UTF-8");
			URL url = new URL(API_URL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			con.disconnect();
			String data = response.toString();
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(data);
			JSONArray parseItems = (JSONArray) obj.get("items");

			YoutubeDto youtubeDto = new YoutubeDto();

			for(int i = 0; i < parseItems.size(); i++) {
				JSONObject item = (JSONObject) parseItems.get(i);
				JSONObject id = (JSONObject) item.get("id");
				String videoId = (String) id.get("videoId");

				JSONObject snippet = (JSONObject) item.get("snippet");
				String title = (String)snippet.get("title");
				JSONObject thumbnails = (JSONObject)snippet.get("thumbnails");
				JSONObject thumbnail = (JSONObject)thumbnails.get("default");
				String thumbnailUrl = (String)thumbnail.get("url");

				Map<String, Object> song = new HashMap<>();
				song.put("title", replaceTitle(title));
				song.put("videoId", videoId);
				song.put("thumbnail", thumbnailUrl);

				youtubeDto.add(song);
			}

			return youtubeDto;

		} catch (IOException | ParseException e) {
			throw new YoutubeApiException("유튜브 노래 검색에 실패하였습니다.");
		}
	}

	// TODO: replaceAll 리팩토링
	public String replaceTitle(String title) {
		title=title.replaceAll("&#39;", "");
		title=title.replaceAll("&lt;", "<");
		title=title.replaceAll("&gt;", ">");
		title=title.replaceAll("&quot;", "");
		title=title.replaceAll("/", "");
		title=title.replaceAll("\\]", "");
		title=title.replaceAll("\\[", "");
		title=title.replaceAll("&amp;", "");
		return title;
	}
}
