package com.myplaylists.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) /* JPA에게 해당 Entity는 Auditiong 기능을 사용함을 알립니다. */
public class Playlist extends BaseTime {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="playlist_id")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	private String title;

	private String description;
	
	@Column(nullable = false)
	private boolean visibility;

	@OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
	private List<Song> songs = new ArrayList<>();

	@Builder
	public Playlist(User user, String title, String description, boolean visibility) {
		this.user = user;
		this.title = title;
		this.description = description;
		this.visibility = visibility;
	}

	public void addSong(Song song) {
		song.setPlaylist(this);
		song.setUserId(this.getUser().getId());
		this.songs.add(song);
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
