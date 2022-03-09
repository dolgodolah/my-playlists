package com.myplaylists.service;

import java.util.List;
import java.util.Optional;

import com.myplaylists.dto.LoginUser;
import com.myplaylists.dto.PlaylistsDto;
import com.myplaylists.dto.SongRequestDto;
import com.myplaylists.dto.SongResponseDto;
import com.myplaylists.exception.ApiException;
import com.myplaylists.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.Song;
import com.myplaylists.repository.SongRepository;

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
		Playlist playlist = playlistService.findPlaylistOrElseThrow(Long.valueOf(songRequestDto.getPlaylistId()));
		playlist.addSong(song);
		return songRepository.save(song);
	}

	@Transactional(readOnly = true)
	public Song getSong(Long songId) {
		return songRepository.findById(songId).orElseThrow(() -> new ApiException("해당 곡은 삭제되었거나 존재하지 않는 곡입니다."));
	}

	@Transactional
	public void updateSong(LoginUser user, SongRequestDto songRequestDto, Long songId) {
		Song song = getSong(songId);
		song.updateSongDetail(songRequestDto, user.getId());
		songRepository.save(song);
	}


	@Transactional
	public void deleteSong(LoginUser user, Long songId) {
		Song song = getSong(songId);
		song.validateUser(user.getId());
		songRepository.delete(song);
	}
}
