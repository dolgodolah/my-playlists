package com.todaymusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.todaymusic.domain.Member;
import com.todaymusic.domain.Playlist;

@EnableJpaAuditing
@SpringBootApplication
public class TodayMusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodayMusicApplication.class, args);
	}

}
