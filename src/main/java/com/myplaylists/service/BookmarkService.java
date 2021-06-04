package com.myplaylists.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.Bookmark;
import com.myplaylists.repository.BookmarkRepository;

@Service
@Transactional
public class BookmarkService {
	
	private final BookmarkRepository bookmarkRepository;
	
	@Autowired
	public BookmarkService(BookmarkRepository bookmarkRepository) {
		this.bookmarkRepository = bookmarkRepository;
	}

	public Long setBookmark(Bookmark bookmark) {
		return bookmarkRepository.save(bookmark).getId();
	}
	
	public void deleteBookmark(Bookmark bookmark) {
		bookmarkRepository.deleteById(bookmark.getId());
	}
	
	public Optional<Bookmark> validateBookmark(Long userId, Long playlistId) {
		return bookmarkRepository.findByUserIdAndPlaylistId(userId, playlistId);
	}

}
