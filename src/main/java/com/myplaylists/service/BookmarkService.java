package com.myplaylists.service;

import java.util.Optional;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.Bookmark;
import com.myplaylists.repository.BookmarkRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
	
	private final BookmarkRepository bookmarkRepository;
	private final UserService userService;
	private final PlaylistService playlistService;

	public void toggleBookmark(Long userId, Long playlistId) {
		Optional<Bookmark> result = validateBookmark(userId, playlistId);
		if (result.isPresent()) {
			deleteBookmark(result.get());
		}else {
			User user = userService.getUser(userId);
			Playlist playlist = playlistService.getPlaylist(playlistId);
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
