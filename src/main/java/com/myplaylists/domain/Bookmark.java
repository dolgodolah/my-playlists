package com.myplaylists.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
@EntityListeners(AuditingEntityListener.class)
public class Bookmark {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bookmark_id")
	public Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="playlist_id")
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
