package com.myplaylists.domain;

import javax.persistence.*;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Setter;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bookmark {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bookmark_id")
	public Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(
			name="user_id",
			nullable = false,
			foreignKey = @ForeignKey(
					name = "FK_USER_ID_BOOKMARK",
					foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE"
			)
	)
	public User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(
			name="playlist_id",
			nullable = false,
			foreignKey = @ForeignKey(
					name = "FK_PLAYLIST_ID_BOOKMARK",
					foreignKeyDefinition = "FOREIGN KEY (playlist_id) REFERENCES playlist(playlist_id) ON DELETE CASCADE"
			)
	)
	public Playlist playlist;

	@CreatedDate
	@Column(name = "created_date")
	public LocalDateTime createdDate;

	@Builder
	public Bookmark(Playlist playlist, User user) {
		this.playlist = playlist;
		this.user = user;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist=playlist;
	}

}
