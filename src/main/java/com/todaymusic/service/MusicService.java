package com.todaymusic.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.todaymusic.domain.entity.LikeKey;
import com.todaymusic.domain.entity.Music;
import com.todaymusic.domain.repository.LikeRepository;
import com.todaymusic.domain.repository.MusicRepository;
import com.todaymusic.dto.LikeDTO;
import com.todaymusic.dto.MusicDTO;


@Service
public class MusicService {
	
	private MusicRepository musicRepository;
	private LikeRepository likeRepository;

	public MusicService(MusicRepository musicRepository, LikeRepository likeRepository) {
		this.musicRepository = musicRepository;
		this.likeRepository = likeRepository;
	}
	
	@Transactional
	public Long saveMusic(MusicDTO musicDTO) {
		return musicRepository.save(musicDTO.toEntity()).getId();
	}
	
	@Transactional
	public Page<Music> getMusicList(Pageable pageable, String pty, String sky){
		if (pty.equals("0")) { //비나 눈이 오지 않은 채
			if (sky.equals("1")) //맑으면
				return musicRepository.findBySky("1", pageable);
			else //맑지 않으면(흐리거나 구름 많을 때)
				return musicRepository.findBySky("4", pageable);
		}
		else {
			if (pty.equals("1")||pty.equals("4")||pty.equals("5")) //비올 때
				return musicRepository.findByPty("1",pageable);
			else if(pty.equals("2")||pty.equals("3")||pty.equals("6")) //눈올 때
				return musicRepository.findByPty("2", pageable);
			else return musicRepository.findAll(pageable);
		}
		
	}
	
	
	@Transactional
	public MusicDTO getMusic(Long id) {
		Music music = musicRepository.findById(id).get();
		MusicDTO musicDTO = MusicDTO.builder()
				.id(music.getId())
				.title(music.getTitle())
				.artist(music.getArtist())
				.likeCount(music.getLikeCount())
				.build();
		return musicDTO;
	}
	
	@Transactional
	public int setLikeCount(Long id) {
		
		//HttpServletRequest에 있는 getMemoteAddr() 메서드를 통해 userIp를 가져온다.
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) ip = req.getRemoteAddr();
		
		//해당 음악(id)과 유저IP에 대한 객체 생성을 한다.
		LikeKey likeKey = new LikeKey();
		likeKey.setMusicId(id);
		likeKey.setUserIp(ip);
		
		//like DB에 해당 음악에 대한 유저IP가 기록되어있는지 확인한다.
		if (likeRepository.findByLikeKey(likeKey).isPresent() == false){
			LikeDTO likeDTO = new LikeDTO();
			likeDTO.setLikeKey(likeKey);
			likeRepository.save(likeDTO.toEntity());
			
			Music music = musicRepository.findById(id).get();
			music.setLikeCount(music.getLikeCount()+1);
			//해당 음악에 처음 좋아요를 눌렀다면 1을 반환한다.
			return 1;
		}
		else { 
			//해당 음악에 좋아요를 누른 적이 있다면 -1을 반환한다.
			return -1;
		}
		
	}
}
