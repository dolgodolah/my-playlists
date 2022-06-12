package com.myplaylists.domain;


import javax.persistence.*;

import com.myplaylists.dto.SongUpdateRequestDto;
import com.myplaylists.exception.ApiException;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class) /* JPA에게 해당 Entity는 Auditiong 기능을 사용함을 알립니다. */
@NoArgsConstructor
public class Song extends BaseTime {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="song_id")
	public Long id;
	public Long userId;
	public String title;
	public String videoId;
	public String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name="playlist_id",
			nullable = false,
			foreignKey = @ForeignKey(
					name = "FK_PLAYLIST_ID_SONG",
					foreignKeyDefinition = "FOREIGN KEY (playlist_id) REFERENCES playlist(playlist_id) ON DELETE CASCADE"
			)
	)
	public Playlist playlist;

	public Song(String title, String videoId) {
		this.title = title;
		this.videoId = videoId;
	}

	public void updateSongDetail(SongUpdateRequestDto song) {
		updateTitle(song.getTitle());
		updateDescription(song.getDescription());
	}

	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateDescription(String description) {
		this.description = description;
	}

	private boolean isSameUser(Long userId) {
		return getUserId() == userId;
	}

	/**
	 * 해당 유저가 추가한 노래가 맞는지 검증
	 */
	public void validateUser(Long userId) {
		if (!isSameUser(userId)) {
			throw new ApiException("잘못된 요청입니다.", 1);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Song song = (Song) o;
		return id.equals(song.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
