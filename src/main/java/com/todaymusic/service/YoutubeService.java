package com.todaymusic.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
public class YoutubeService {
	
	@Transactional
	public String getVideoId(String search) throws IOException, ParseException {
		String apiurl = "https://www.googleapis.com/youtube/v3/search";
		apiurl += "?key=AIzaSyBoG1IdXuY99quT1XwdPIDnPuUS51t2TpA";
		apiurl += "&part=id&type=video&maxResults=1&videoEmbeddable=true";
		//search = db에서 받은 검색어(타이틀 + 아티스트)
		apiurl += "&q="+URLEncoder.encode(search,"UTF-8");
		
		URL url = new URL(apiurl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		String inputLine;
		StringBuffer sb = new StringBuffer();
		while((inputLine = br.readLine()) != null) {
			sb.append(inputLine);
		}
		br.close();
		conn.disconnect();
//		System.out.println(sb);
		String data = sb.toString();
		JSONParser parser = new JSONParser(); 
		JSONObject obj = (JSONObject) parser.parse(data); 
		JSONArray parse_items = (JSONArray) obj.get("items");
		JSONObject video;
		JSONObject Id = null;
		String videoId = null;
		for(int i=0;i<parse_items.size();i++) {
//			System.out.println(parse_items.get(i));
			video = (JSONObject) parse_items.get(i);
			Id = (JSONObject) video.get("id");
//			System.out.println(Id);
			videoId=(String)Id.get("videoId");
		}
		
		//해당 검색어의 유튜브 영상 중 첫번째의 videoId 반환
		return videoId;	
	}

}
