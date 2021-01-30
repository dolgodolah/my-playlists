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
		List<Music> musicList = musicRepository.findByPty(pty);//db에 해당 pty 찾아서 musicList에 저장
    	//찾은 게시물을 담을 boardDTOList 생성
    	List<MusicDTO> musicDTOList = new ArrayList<>();//찾은 musicList들에 대한 정보를 담을 DTOList 생성
    	for(Music music : musicList) {
    		//유튜브 검색을 위한 정보만 필요하기 때문에 제목+가수명만 추출한다.
    		MusicDTO musicDTO = MusicDTO.builder()
    				.id(music.getId())
    				.title(music.getTitle())
    				.artist(music.getArtist())
    				.build();
    		musicDTOList.add(musicDTO);
    	}
    	return musicDTOList;
	}
	
	@Transactional
	public MusicDTO getMusic(Long id) {
		Music music = musicRepository.findById(id).get();
		MusicDTO musicDTO = MusicDTO.builder()
				.id(music.getId())
				.title(music.getTitle())
				.artist(music.getArtist())
				.build();
		return musicDTO;
	}
}
