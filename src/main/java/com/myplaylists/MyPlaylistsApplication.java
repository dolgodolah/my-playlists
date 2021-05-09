package com.myplaylists;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;

@EnableJpaAuditing
@SpringBootApplication
public class MyPlaylistsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyPlaylistsApplication.class, args);
	}
	
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}

}
