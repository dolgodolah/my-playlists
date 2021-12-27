package com.myplaylists.service;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.Bookmark;
import com.myplaylists.repository.BookmarkRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

	private final PlaylistService playlistService;
	private final UserService userService;
	private final BookmarkRepository bookmarkRepository;

	public void toggleBookmark(Long userId, Long playlistId) {
		bookmarkRepository.findAllByUserId(userId).stream()
			.filter(bookmark -> bookmark.getPlaylist().getId().equals(playlistId))
			.findAny()
			.ifPresentOrElse(this::deleteBookmark, () -> addBookmark(userId, playlistId));
	}

	public Page<Bookmark> findByUserId(Long userId, Pageable pageable) {
		return bookmarkRepository.findByUserId(pageable, userId);
	}

	public void deleteBookmark(Bookmark bookmark) {
		bookmarkRepository.deleteById(bookmark.getId());
	}
	
	public boolean checkBookmark(Long userId, Long playlistId) {
		return bookmarkRepository.findByUserIdAndPlaylistId(userId, playlistId).isPresent();
	}

	private void addBookmark(Long userId, Long playlistId) {
		User user = userService.findUserOrElseThrow(userId);
		Playlist playlist = playlistService.findPlaylistOrElseThrow(playlistId);
		Bookmark bookmark = Bookmark.builder()
				.playlist(playlist)
				.build();
		user.addBookmark(bookmark);
	}

}
