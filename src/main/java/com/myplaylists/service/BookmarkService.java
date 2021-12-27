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

	@Transactional(readOnly = true)
	public Optional<Bookmark> findAllByUserId(Long userId) {
		return bookmarkRepository.findAllByUserId(userId);
	}

	@Transactional(readOnly = true)
	public Page<Bookmark> findByUserId(Long userId, Pageable pageable) {
		return bookmarkRepository.findByUserId(pageable, userId);
	}

	@Transactional
	public void deleteBookmark(Long bookmarkId) {
		bookmarkRepository.deleteById(bookmarkId);
	}

	@Transactional(readOnly = true)
	public boolean checkBookmark(Long userId, Long playlistId) {
		return bookmarkRepository.findByUserIdAndPlaylistId(userId, playlistId).isPresent();
	}

	public void addBookmark(User user, Playlist playlist) {
		Bookmark bookmark = Bookmark.builder()
				.playlist(playlist)
				.user(user)
				.build();

		bookmarkRepository.save(bookmark);
	}
}
