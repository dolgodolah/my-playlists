package com.myplaylists.service;


import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.dto.SongRequestDto;
import com.myplaylists.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.Song;
import com.myplaylists.repository.SongRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

	private final PlaylistService playlistService;
	private final SongRepository songRepository;

	@Transactional
	public Song addSong(SongRequestDto songRequestDto) {
		Song song = Song.builder()
				.title(songRequestDto.getTitle())
				.videoId(songRequestDto.getVideoId())
				.build();
		Playlist playlist = playlistService.findPlaylistByIdOrElseThrow(songRequestDto.getPlaylistId());
		playlist.addSong(song);
		return songRepository.save(song);
	}

	@Transactional(readOnly = true)
	public Song getSong(Long songId) {
		return songRepository.findById(songId).orElseThrow(() -> new ApiException("해당 곡은 삭제되었거나 존재하지 않는 곡입니다.", 1));
	}

	@Transactional
	public void updateSong(LoginUser user, SongRequestDto songRequestDto, Long songId) {
		Song song = getSong(songId);
		song.updateSongDetail(songRequestDto, user.getUserId());
		songRepository.save(song);
	}

	@Transactional
	public void deleteSong(LoginUser user, Long songId) {
		Song song = getSong(songId);
		song.validateUser(user.getUserId());

		Playlist playlist = song.getPlaylist();
		playlist.deleteSong(song);
		songRepository.delete(song);
	}

	public List<Song> findSongsByPlaylistId(Long playlistId) {
		Playlist playlist = playlistService.findPlaylistByIdOrElseThrow(playlistId);
		return songRepository.findSongsByPlaylist(playlist);
	}
}
