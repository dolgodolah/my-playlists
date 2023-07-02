package com.myplaylists.service;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;
import com.myplaylists.exception.NotFoundException;
import com.myplaylists.repository.PlaylistJpaRepository;
import com.myplaylists.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.Bookmark;
import com.myplaylists.repository.BookmarkRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;
	private final UserRepository userRepository;
	private final PlaylistJpaRepository playlistJpaRepository;

	@Transactional(readOnly = true)
	public Page<Bookmark> findByUserId(Long userId, Pageable pageable) {
		return bookmarkRepository.findByUserId(pageable, userId);
	}

	@Transactional(readOnly = true)
	public int getBookmarkCount(Long playlistId) {
		return bookmarkRepository.findAllByPlaylistId(playlistId).size();
	}

	@Transactional(readOnly = true)
	public boolean isBookmark(Long userId, Long playlistId) {
		return bookmarkRepository.findByUserIdAndPlaylistId(userId, playlistId).isPresent();
	}

	@CacheEvict(key = "#userId", value = "playlist")
	public void toggleBookmark(Long userId, Long playlistId) {
		bookmarkRepository.findByUserIdAndPlaylistId(userId, playlistId)
				.ifPresentOrElse(
						bookmark -> deleteBookmark(bookmark.id),
						() -> addBookmark(userId, playlistId)
				);
	}

	private void addBookmark(Long userId, Long playlistId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("해당 사용자는 존재하지 않는 사용자입니다."));
		Playlist playlist = playlistJpaRepository.findById(playlistId).orElseThrow(() -> new NotFoundException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다."));

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
