package com.myplaylists.service;

import com.myplaylists.dto.LoginUser;
import com.myplaylists.dto.PlaylistRequestDto;
import com.myplaylists.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;
import com.myplaylists.repository.PlaylistRepository;

@Service
@RequiredArgsConstructor
public class PlaylistService {

	private final UserService userService;
	private final PlaylistRepository playlistRepository;

	@Transactional
	public Playlist addPlaylist(LoginUser loginUser, PlaylistRequestDto playlistRequestDto) {
		User user = userService.findByUserId(loginUser.getUserId());
		Playlist playlist = playlistRequestDto.toEntity(user);
		return playlistRepository.save(playlist);

	}

	@Transactional(readOnly = true)
	public Page<Playlist> findMyPlaylists(Pageable pageable, Long userId) {
		User user = userService.findByUserId(userId);
		return playlistRepository.findAllByUserOrderByUpdatedDateDesc(pageable, user);
	}

	@Transactional(readOnly = true)
	public Page<Playlist> findAllPlaylists(Pageable pageable){
		return playlistRepository.findByVisibility(pageable, true);
	}

	@Transactional
	public void deletePlaylist(LoginUser user, Long playlistId) {
		Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new ApiException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다."));
		playlist.validateUser(user.getUserId());
		playlistRepository.delete(playlist);
	}

	@Transactional(readOnly = true)
	public Page<Playlist> searchMyPlaylists(Pageable pageable, String keyword, Long userId){
		User user = userService.findByUserId(userId);
		return playlistRepository.findByTitleContainingAndUser(pageable, keyword, user);
	}

	@Transactional(readOnly = true)
	public Page<Playlist> searchAll(Pageable pageable, String keyword){
		return playlistRepository.findByTitleContaining(pageable, keyword);
	}

	@Transactional(readOnly = true)
	public Playlist findPlaylist(Long playlistId) {
		return playlistRepository.findById(playlistId).orElseThrow(() -> new ApiException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다."));
	}
}
