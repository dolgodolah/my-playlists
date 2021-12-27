package com.myplaylists.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.myplaylists.dto.LoginUser;
import com.myplaylists.dto.PlaylistDto;
import com.myplaylists.dto.PlaylistRequestDto;
import com.myplaylists.dto.PlaylistsDto;
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

	private final UserService userService;
	private final BookmarkService bookmarkService;
	private final PlaylistRepository playlistRepository;

	@Transactional
	public PlaylistDto addPlaylist(LoginUser loginUser, PlaylistRequestDto playlistRequestDto) {
		String title = playlistRequestDto.getTitle();
		if (!StringUtils.hasText(title)) {
			throw new ApiException("플레이리스트 제목을 입력해주세요.");
		}
		if (title.length() > 30) {
			throw new ApiException("최대 30자까지 가능합니다.");
		}

		User user = userService.findUserOrElseThrow(loginUser.getId());
		Playlist playlist = playlistRequestDto.toEntity(user);
		return PlaylistDto.of(playlistRepository.save(playlist));

	}

	@Transactional(readOnly = true)
	public PlaylistsDto findMyPlaylists(Pageable pageable, Long userId) {
		User user = userService.findUserOrElseThrow(userId);
		Page<Playlist> playlists = playlistRepository.findAllByUser(pageable, user);
		return PlaylistsDto.of(playlists);
	}

	@Transactional(readOnly = true)
	public PlaylistsDto findAllPlaylists(Pageable pageable){
		Page<Playlist> playlists = playlistRepository.findByVisibility(pageable, true);
		return PlaylistsDto.of(playlists);
	}

	public List<PlaylistDto> findBookmarkedPlaylists(Long userId, Pageable pageable) {
		Page<Bookmark> bookmarks = bookmarkService.findByUserId(userId, pageable);
		return bookmarks.stream()
				.map(Bookmark::getPlaylist)
				.map(PlaylistDto::of)
				.collect(Collectors.toList());
	}

	@Transactional
	public void deletePlaylist(Long playlistId) {
		Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new RuntimeException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다."));
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
	public PlaylistDto findPlaylist(Long userId, Long playlistId) {
		Playlist playlist = findPlaylistOrElseThrow(playlistId);
		boolean isBookmark = bookmarkService.checkBookmark(userId, playlistId);

		PlaylistDto playlistDto = PlaylistDto.of(playlist);
		playlistDto.setBookmark(isBookmark);
		return playlistDto;
	}

	public Playlist findPlaylistOrElseThrow(Long playlistId) {
		return playlistRepository.findById(playlistId).orElseThrow(() -> new RuntimeException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다."));
	}

	@Transactional
	public void toggleBookmark(Long userId, Long playlistId) {
		User user = userService.findUserOrElseThrow(userId);
		Playlist playlist = findPlaylistOrElseThrow(playlistId);

		bookmarkService.findAllByUserId(userId).stream()
				.filter(bookmark -> bookmark.getPlaylist().equals(playlist))
				.findAny()
				.ifPresentOrElse(bookmark -> {
					bookmarkService.deleteBookmark(bookmark.getId());
				}, () -> {
					bookmarkService.addBookmark(user, playlist);
				});
	}
}
