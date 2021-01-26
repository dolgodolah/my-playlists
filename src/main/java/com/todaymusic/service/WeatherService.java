package com.todaymusic.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
		Calendar cal = Calendar.getInstance();
		int t = cal.get(Calendar.HOUR_OF_DAY);
		int mTime = 0;
		String mTime2 = null;
		String mDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		//현재 시간이 0시~0시59분이면 23시~23시59분으로
		if (t==0) { 
			cal.add(Calendar.DATE, -1);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			Date date = cal.getTime();
			mDate = sdf.format(date);
			mTime = cal.get(Calendar.HOUR_OF_DAY);
			mTime2=mTime+"00";
		}
		else
		{
			cal.set(Calendar.HOUR_OF_DAY, t);
			Date date = cal.getTime();
			mDate = sdf.format(date);
			mTime = cal.get(Calendar.HOUR_OF_DAY);
			if(mTime<=10) // 1030 -> 0900
			{
				mTime2 = "0"+(mTime-1)+"00";
			}
			else // 1830 -> 1700
			{
				mTime2 = (mTime-1)+"00";
			}
		}
		
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
                .queryParam("base_date", mDate)
                .queryParam("base_time", mTime2)
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
