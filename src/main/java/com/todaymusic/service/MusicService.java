package com.todaymusic.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.todaymusic.domain.entity.Music;
import com.todaymusic.domain.repository.MusicRepository;
import com.todaymusic.dto.MusicDTO;

@Service
public class MusicService {
	private MusicRepository musicRepository;

	public MusicService(MusicRepository musicRepository) {
		this.musicRepository = musicRepository;
	}
	
	@Transactional
	public Long saveMusic(MusicDTO musicDTO) {
		return musicRepository.save(musicDTO.toEntity()).getId();
	}
	
	@Transactional
    public List<MusicDTO> getMusicList(String pty){
    	//db에 있는 게시물 전부 찾아 boardList에 저장
    	List<Music> musicList = musicRepository.findByPty(pty);
    	//찾은 게시물을 담을 boardDTOList 생성
    	List<MusicDTO> musicDTOList = new ArrayList<>();
    	
    	for(Music music : musicList) {
    		MusicDTO musicDTO = MusicDTO.builder()
    				.id(music.getId())
    				.title(music.getTitle())
    				.artist(music.getArtist())
    				.build();
    		musicDTOList.add(musicDTO);
    	}
    	return musicDTOList;
    }
	
}
