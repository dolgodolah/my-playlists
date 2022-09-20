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
		return bookmarkRepository.findAllByUserId(userId);
	}

	@Transactional(readOnly = true)
	public Page<Bookmark> findByUserId(Long userId, Pageable pageable) {
		return bookmarkRepository.findByUserId(pageable, userId);
	}

	@Transactional(readOnly = true)
	public BookmarkDto checkBookmark(Long userId, Long playlistId) {
		boolean isBookmark = bookmarkRepository.findByUserIdAndPlaylistId(userId, playlistId).isPresent();
		return BookmarkDto.Companion.of(isBookmark);
	}

	public void toggleBookmark(Long userId, Long playlistId) {
		bookmarkRepository.findByUserIdAndPlaylistId(userId, playlistId)
				.ifPresentOrElse(
						bookmark -> deleteBookmark(bookmark.id),
						() -> addBookmark(userId, playlistId)
				);
	}

	private void addBookmark(Long userId, Long playlistId) {
		User user = userService.findUserByIdOrElseThrow(userId);
		Playlist playlist = playlistService.findPlaylistByIdOrElseThrow(playlistId);

		Bookmark bookmark = Bookmark.builder()
				.playlist(playlist)
				.user(user)
				.build();

		bookmarkRepository.save(bookmark);
	}

	private void deleteBookmark(Long bookmarkId) {
		bookmarkRepository.deleteById(bookmarkId);
	}
}
