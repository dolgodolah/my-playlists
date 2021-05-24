package com.myplaylists.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.Bookmark;
import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.Song;
import com.myplaylists.domain.User;
import com.myplaylists.repository.BookmarkRepository;
import com.myplaylists.repository.PlaylistRepository;
import com.myplaylists.repository.SongRepository;

@Service
@Transactional
public class PlaylistService {
	
	private PlaylistRepository playlistRepository;
	private SongRepository songRepository;
	private BookmarkRepository bookmarkRepository;
	
	
	@Autowired
	public PlaylistService(PlaylistRepository playlistRepository, SongRepository songRepository, BookmarkRepository bookmarkRepository) {
		this.playlistRepository = playlistRepository;
		this.songRepository = songRepository;
		this.bookmarkRepository = bookmarkRepository;
	}


	@Transactional
	public Long addPlaylist(Playlist playlist) {
		return playlistRepository.save(playlist).getId();	}
	
	public Page<Playlist> findMyPlaylists(Pageable pageable, User user) {
		return playlistRepository.findByUserId(pageable, user.getId());
	}
	
	public Page<Playlist> findAllPlaylists(Pageable pageable){
		return playlistRepository.findByVisibility(pageable, false);
	}
	
	public void deletePlaylist(Playlist playlist) {
		playlistRepository.deleteById(playlist.getId());
	}
	
	public Page<Playlist> search(Pageable pageable, String keyword, User user){
		return playlistRepository.findByTitleContainingAndUserId(pageable, keyword, user.getId());
	}
	
	public Playlist getPlaylist(Long id) {
		return playlistRepository.findById(id).get();
	}
	
	public Long setBookmark(Bookmark bookmark) {
		return bookmarkRepository.save(bookmark).getId();
	}
	
	public void deleteBookmark(Bookmark bookmark) {
		bookmarkRepository.deleteById(bookmark.getId());
	}
	
	public List<Playlist> findBookmarkPlaylists(Pageable pageable, User user){
		List<Bookmark> bookmarks = user.getBookmarks();
		List<Playlist> playlists = new ArrayList<>();
		bookmarks.forEach(bookmark -> playlists.add(getPlaylist(bookmark.getPlaylistId())));
		return playlists;
	}
	
	public Optional<Bookmark> validateBookmark(Long userId, Long playlistId) {
		return bookmarkRepository.findByUserIdAndPlaylistId(userId, playlistId);
	}
	


}
