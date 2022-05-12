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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final UserService userService;
	private final PlaylistService playlistService;

	@Transactional(readOnly = true)
	public Optional<Bookmark> findAllByUserId(Long userId) {
		User user = userService.findByUserId(userId);
		return bookmarkRepository.findAllByUser(user);
	}

	@Transactional(readOnly = true)
	public Page<Bookmark> findByUserId(Long userId, Pageable pageable) {
		User user = userService.findByUserId(userId);
		return bookmarkRepository.findByUser(pageable, user);
	}

	@Transactional
	public void deleteBookmark(Long bookmarkId) {
		bookmarkRepository.deleteById(bookmarkId);
	}

	@Transactional(readOnly = true)
	public boolean checkBookmark(Long userId, Long playlistId) {
		User user = userService.findByUserId(userId);
		return bookmarkRepository.findByUserAndPlaylistId(user, playlistId).isPresent();
	}

	public void addBookmark(User user, Playlist playlist) {
		Bookmark bookmark = Bookmark.builder()
				.playlist(playlist)
				.user(user)
				.build();

		bookmarkRepository.save(bookmark);
	}

	@Transactional
	public void toggleBookmark(Long userId, Long playlistId) {
		User user = userService.findByUserId(userId);
		Playlist playlist = playlistService.findPlaylistByIdOrElseThrow(playlistId);

		findAllByUserId(userId).stream()
				.filter(bookmark -> bookmark.getPlaylist().equals(playlist))
				.findAny()
				.ifPresentOrElse(bookmark -> {
					deleteBookmark(bookmark.getId());
				}, () -> {
					addBookmark(user, playlist);
				});
	}
}
