package com.myplaylists.domain;

import java.util.Objects;

import javax.persistence.*;

import com.myplaylists.exception.ApiException;
import lombok.Builder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) /* JPA에게 해당 Entity는 Auditiong 기능을 사용함을 알립니다. */
public class Playlist extends BaseTime {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="playlist_id")
	public Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(
			name="user_id",
			nullable = false,
			foreignKey = @ForeignKey(
					name = "FK_USER_ID_PLAYLIST",
					foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE"
			)
	)
	public User user;


	public String author;
	public String title;
	public String description;
	
	@Column(nullable = false)
	public boolean visibility;

	public int songCount;

	@Builder
	public Playlist(User user, String title, String description, boolean visibility) {
		if (!StringUtils.hasText(title)) {
			throw new ApiException("플레이리스트 제목을 입력해주세요.", 1);
		}

		if (title.length() > 50) {
			throw new ApiException("플레이리스트 제목은 최대 50자까지 가능합니다.", 1);
		}

		this.user = user;
		this.author = user.getNickname();
		this.title = title;
		this.description = description;
		this.visibility = visibility;
		this.songCount = 0;
	}

	public void addSong(Song song) {
		song.setPlaylist(this);
		song.setUserId(this.getUser().getUserId());
		songCount++;
	}

	public void deleteSong(Song song) {
		song.setPlaylist(null);
		song.setUserId(null);
		songCount--;
	}

	public boolean isSameUser(Long userId) {
		return getUser().getUserId() == userId;
	}

	/**
	 * 해당 유저가 추가한 플레이리스트가 맞는지 검증
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
		Playlist playlist = (Playlist) o;
		return Objects.equals(id, playlist.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
