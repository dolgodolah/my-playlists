package com.myplaylists.service;

import java.util.List;

import com.myplaylists.dto.SongRequestDto;
import com.myplaylists.dto.SongResponseDto;
import com.myplaylists.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.Song;
import com.myplaylists.repository.SongRepository;

@Service
@RequiredArgsConstructor
public class SongService {
	
	private final SongRepository songRepository;
	private final PlaylistService playlistService;

	@Transactional
	public SongResponseDto addSong(SongRequestDto songRequestDto) {
		Song song = Song.builder()
				.title(songRequestDto.getTitle())
				.videoId(songRequestDto.getVideoId())
				.build();
		Playlist playlist = playlistService.getPlaylist(Long.valueOf(songRequestDto.getPlaylistId()));
		playlist.addSong(song);
		return SongResponseDto.of(songRepository.save(song));
	}

	@Transactional
	public void updateSong(Long songId, String title, String description) {
		Song song = getSong(songId);
		song.updateTitle(title);
		song.updateDescription(description);
		songRepository.save(song);
	}
	
	@Transactional
	public void deleteSong(Long songId) {
		Song song = getSong(songId);
		songRepository.delete(song);
	}

	@Transactional(readOnly = true)
	public List<Song> getSongs(Playlist playlist){
		return songRepository.findAllByPlaylistOrderByIdDesc(playlist);
	}

	@Transactional(readOnly = true)
	public Song getSong(Long songId) {
		return songRepository.findById(songId).orElseThrow(() -> new RuntimeException("해당 곡은 삭제되었거나 존재하지 않는 곡입니다."));
	}
	
}
