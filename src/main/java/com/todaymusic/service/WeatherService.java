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
                .path("/1360000/VilageFcstInfoService/getUltraSrtFcst")
                .queryParam("ServiceKey", "HBcnUdoF6rQ%2FdMzn3lAzaePET89TOJLA%2BcZ4h4cE34%2BLhNyplVyQqIhDZaehWm624XorqaFWyXtx0t%2Fo0AJKQQ%3D%3D")
                .queryParam("nx", "60")
                .queryParam("ny", "127")
                .queryParam("base_date", mDate)
                .queryParam("base_time", mTime2)
                .queryParam("dataType", "JSON")
                .queryParam("numOfRows", "50");

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
		String fcstValue;
		JSONObject weather;
		HashMap<String, String> result = new HashMap<>();
		
		for(int i=0;i<parse_item.size();i++) {
			//System.out.println(parse_item.get(i));
			weather = (JSONObject) parse_item.get(i);
			fcstValue = (String)weather.get("fcstValue");
			category = (String)weather.get("category");
//			System.out.println(category + fcstValue);
			
			//6시간 이후 날씨 정보까지 1시간 단위로 받아오기 때문에 현재시간으로부터 가장 가까운 시간의 관측값을 result에 넣는다.
			if (result.containsKey(category)==false) {
				result.put(category, fcstValue);
			}
		}
//		System.out.println(result); //날씨예보 확인
		
		//- 하늘상태(SKY) 코드 : 맑음(1), 구름많음(3, 4로 정규화), 흐림(4)
		//- 강수형태(PTY) 코드 : 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4), 빗방울(5), 빗방울/눈날림(6), 눈날림(7)	
		//PTY==0 and SKY==1 : 날씨 맑을 때 듣기 좋은 음악 추천
		//PTY==0 and (SKY==3 or SKY==4) : 구름많음..
		//PTY==0 and SKY==4 : 흐림..
		//PTY==1 or PTY==4 or PTY==5 : 비올 때 듣기 좋은 음악 추천(1로 정규화)
		//PTY==2 or PTY==3 or PTY==6 or PTY==7(2로 정규화)
		return result;
	}

}
