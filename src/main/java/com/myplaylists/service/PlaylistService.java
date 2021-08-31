package com.myplaylists.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.myplaylists.dto.LoginUser;
import com.myplaylists.dto.PlaylistRequestDto;
import com.myplaylists.dto.PlaylistResponseDto;
import com.myplaylists.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.Bookmark;
import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;
import com.myplaylists.repository.PlaylistRepository;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PlaylistService {
	
	private final PlaylistRepository playlistRepository;
	private final UserService userService;

	@Transactional
	public PlaylistResponseDto addPlaylist(LoginUser loginUser, PlaylistRequestDto playlistRequestDto) {
		String title = playlistRequestDto.getTitle();
		if (!StringUtils.hasText(title)) {
			throw new ApiException("플레이리스트 제목을 입력해주세요.");
		}
		if (title.length() > 30) {
			throw new ApiException("최대 30자까지 가능합니다.");
		}

		User user = userService.getUserEntity(loginUser.getId());
		Playlist playlist = playlistRequestDto.toEntity(user);
		return PlaylistResponseDto.of(playlistRepository.save(playlist));
	}

	@Transactional(readOnly = true)
	public List<PlaylistResponseDto> findMyPlaylists(Pageable pageable, Long userId) {
		User user = userService.getUserEntity(userId);
		List<Playlist> playlists = playlistRepository.findAllByUser(pageable, user);
		return playlists.stream()
				.map(PlaylistResponseDto::of)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Long getMyPlaylistsSize(Long userId) {
		User user = userService.getUserEntity(userId);
		return playlistRepository.countAllByUser(user);
	}

	@Transactional(readOnly = true)
	public List<PlaylistResponseDto> findAllPlaylists(Pageable pageable, boolean visibility){
		List<Playlist> playlists = playlistRepository.findByVisibility(pageable, visibility);
		return playlists.stream()
				.map(PlaylistResponseDto::of)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Long getAllPlaylistsSize(boolean visibility) {
		return playlistRepository.countAllByVisibility(visibility);
	}

	@Transactional
	public void deletePlaylist(Long playlistId) {
		Playlist playlist = getPlaylist(playlistId);
		playlistRepository.delete(playlist);
	}

	@Transactional(readOnly = true)
	public Page<Playlist> searchMylist(Pageable pageable, String keyword, Long userId){
		return playlistRepository.findByTitleContainingAndUserId(pageable, keyword, userId);
	}

	@Transactional(readOnly = true)
	public Page<Playlist> searchAll(Pageable pageable, String keyword){
		return playlistRepository.findByTitleContaining(pageable, keyword);
	}

	@Transactional(readOnly = true)
	public Playlist getPlaylist(Long playlistId) {
		return playlistRepository.findById(playlistId).orElseThrow(() -> new RuntimeException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다."));
	}
	
	
	
	public List<Playlist> findBookmarkPlaylists(Pageable pageable, User user){
		List<Bookmark> bookmarks = user.getBookmarks();
		List<Playlist> playlists = new ArrayList<>();
		bookmarks.forEach(bookmark -> playlists.add(bookmark.getPlaylist()));
		return playlists;
	}
	
	
	


}
