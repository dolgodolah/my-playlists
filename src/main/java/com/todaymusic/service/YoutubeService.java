package com.todaymusic.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.todaymusic.dto.YoutubeForm;

@Service
public class YoutubeService {
	
	public List<YoutubeForm> search(String search) throws IOException, ParseException {
		String apiurl = "https://www.googleapis.com/youtube/v3/search";
		apiurl += "?key=AIzaSyCh8po8zIqwbllXzP5P3_gsHi_7292StBs";
		apiurl += "&part=snippet&type=video&maxResults=3&videoEmbeddable=true";
		apiurl += "&q="+URLEncoder.encode(search,"UTF-8");
		
		URL url = new URL(apiurl);
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
		JSONArray parse_items = (JSONArray) obj.get("items");
		JSONObject video;
		JSONObject Id = null;
		JSONObject snippet = null;
		String videoId = null;
		String title = null;
		JSONObject thumbnails = null;
		JSONObject thumbnail = null;
		String thumbnailUrl = null;
		List<YoutubeForm> list = new ArrayList<>();
		for(int i=0;i<parse_items.size();i++) {
			video = (JSONObject) parse_items.get(i);
			Id = (JSONObject) video.get("id");
			videoId=(String)Id.get("videoId");
			
			snippet = (JSONObject) video.get("snippet");
			title = (String)snippet.get("title");
			thumbnails = (JSONObject)snippet.get("thumbnails");
			thumbnail = (JSONObject)thumbnails.get("default");
			thumbnailUrl = (String)thumbnail.get("url");
			YoutubeForm youtubeForm = new YoutubeForm();
			
			title=replace(title);
			youtubeForm.setTitle(title);
			youtubeForm.setVideoId(videoId);
			youtubeForm.setThumbnail(thumbnailUrl);
			list.add(youtubeForm);
		}
		
		
		return list;
	}
	
	public String replace(String title) {
		title=title.replaceAll("&#39;", "'");
		title=title.replaceAll("&lt;", "<");
		title=title.replaceAll("&gt;", ">");
		title=title.replaceAll("&quot;", "\"");
		return title;
	}
}
