package com.todaymusic.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	
	
	
	private static final int BLOCK_PAGE_NUM_COUNT = 3;
	private static final int PAGE_POST_COUNT = 5;
	
	@Transactional
	public Long saveMusic(MusicDTO musicDTO) {
		return musicRepository.save(musicDTO.toEntity()).getId();
	}
	
	@Transactional
	public List<MusicDTO> getMusicList(Integer pageNum, String pty){
		Page<Music> page = musicRepository.findByPty(pty, PageRequest.of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC,"likeCount")));
//        Page<Music> page = musicRepository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT));
		List<Music> musicList = page.getContent();
		List<MusicDTO> musicDTOList = new ArrayList<>();
		
    	//찾은 게시물을 담을 boardDTOList 생성
    	for(Music music : musicList) {
    		//유튜브 검색을 위한 정보만 필요하기 때문에 제목+가수명만 추출한다.
    		MusicDTO musicDTO = MusicDTO.builder()
    				.id(music.getId())
    				.title(music.getTitle())
    				.artist(music.getArtist())
    				.likeCount(music.getLikeCount())
    				.build();
    		musicDTOList.add(musicDTO);
    	}
    	return musicDTOList;
	}
	
	@Transactional
	public Long getMusicCount() {
		return musicRepository.count();
	}
	
	public Integer[] getPageList(Integer curPageNum) {
		Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getMusicCount());

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        // 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
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
