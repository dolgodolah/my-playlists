package com.myplaylists.service;

import java.util.Optional;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;
import com.myplaylists.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.Bookmark;
import com.myplaylists.repository.BookmarkRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
	
	private final BookmarkRepository bookmarkRepository;
	private final PlaylistRepository playlistRepository;
	private final UserService userService;

	public void toggleBookmark(Long userId, Long playlistId) {
		Optional<Bookmark> result = validateBookmark(userId, playlistId);
		if (result.isPresent()) {
			deleteBookmark(result.get());
		}else {
			User user = userService.getUserEntity(userId);
			Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new RuntimeException("해당 플레이리스트는 삭제되었거나 존재하지 않는 플레이리스트입니다."));
			Bookmark bookmark = Bookmark.builder()
					.playlist(playlist)
					.build();

			user.addBookmark(bookmark);
		}
	}

	public void deleteBookmark(Bookmark bookmark) {
		bookmarkRepository.deleteById(bookmark.getId());
	}
	
	public Optional<Bookmark> validateBookmark(Long userId, Long playlistId) {
		return bookmarkRepository.findByUserIdAndPlaylistId(userId, playlistId);
	}

}
