package com.myplaylists.service;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;
import com.myplaylists.dto.BookmarkDto;
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
@Transactional
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final UserService userService;
	private final PlaylistService playlistService;

	@Transactional(readOnly = true)
	public Optional<Bookmark> findAllByUserId(Long userId) {
		User user = userService.findUserByIdOrElseThrow(userId);
		return bookmarkRepository.findAllByUser(user);
	}

	@Transactional(readOnly = true)
	public Page<Bookmark> findByUserId(Long userId, Pageable pageable) {
		User user = userService.findUserByIdOrElseThrow(userId);
		return bookmarkRepository.findByUser(pageable, user);
	}

	public void deleteBookmark(Long bookmarkId) {
		bookmarkRepository.deleteById(bookmarkId);
	}

	@Transactional(readOnly = true)
	public BookmarkDto checkBookmark(Long userId, Long playlistId) {
		User user = userService.findUserByIdOrElseThrow(userId);
		Playlist playlist = playlistService.findPlaylistByIdOrElseThrow(playlistId);
		boolean isBookmark = bookmarkRepository.findByUserAndPlaylist(user, playlist).isPresent();
		return BookmarkDto.Companion.of(isBookmark);
	}

	public void addBookmark(User user, Playlist playlist) {
		Bookmark bookmark = Bookmark.builder()
				.playlist(playlist)
				.user(user)
				.build();

		bookmarkRepository.save(bookmark);
	}

	public void toggleBookmark(Long userId, Long playlistId) {
		User user = userService.findUserByIdOrElseThrow(userId);
		Playlist playlist = playlistService.findPlaylistByIdOrElseThrow(playlistId);
		bookmarkRepository.findByUserAndPlaylist(user, playlist)
				.ifPresentOrElse(
						bookmark -> deleteBookmark(bookmark.id),
						() -> addBookmark(user, playlist)
				);
	}
}
