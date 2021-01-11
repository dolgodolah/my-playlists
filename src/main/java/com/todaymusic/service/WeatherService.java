package com.todaymusic.service;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

@Service
public class WeatherService {
	
	@Transactional
	public HashMap<String, String> getItemsFromApi() throws ParseException {
		RestTemplate restTemplate = new RestTemplateBuilder().build();
		String apiurl = "http://apis.data.go.kr";
		DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(apiurl);
		uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        UriBuilder uriBuilder = uriBuilderFactory.builder();
        uriBuilder
                .path("/1360000/VilageFcstInfoService/getUltraSrtNcst")
                .queryParam("ServiceKey", "HBcnUdoF6rQ%2FdMzn3lAzaePET89TOJLA%2BcZ4h4cE34%2BLhNyplVyQqIhDZaehWm624XorqaFWyXtx0t%2Fo0AJKQQ%3D%3D")
                .queryParam("nx", "60")
                .queryParam("ny", "127")
                .queryParam("base_date", "20210111")
                .queryParam("base_time", "2300")
                .queryParam("dataType", "JSON")
                .queryParam("numOfRows", "10");

        ResponseEntity responseEntity = restTemplate.exchange(uriBuilder.build(), HttpMethod.GET, null, String.class);
        String response = (String) responseEntity.getBody();
//        System.out.println(response);
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(response);
		JSONObject parse_response = (JSONObject) obj.get("response"); 
		// response 로 부터 body 찾기
		JSONObject parse_body = (JSONObject) parse_response.get("body"); 
		// body 로 부터 items 찾기 
		JSONObject parse_items = (JSONObject) parse_body.get("items");
		// items 로 부터 item 찾기
		JSONArray parse_item = (JSONArray) parse_items.get("item");
        
		String category;
		String obsr_Value;
		JSONObject weather;
		HashMap<String, String> result = new HashMap<>();
		
		for(int i=0;i<parse_item.size();i++) {
			//System.out.println(parse_item.get(i));
			weather = (JSONObject) parse_item.get(i);
			obsr_Value = (String)weather.get("obsrValue");
			category = (String)weather.get("category");
			//System.out.println(category + obsr_Value);
			result.put(category, obsr_Value);
		}
		return result;
	}

}
