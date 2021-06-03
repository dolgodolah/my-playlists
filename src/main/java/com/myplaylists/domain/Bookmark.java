package com.myplaylists.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Setter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Bookmark {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bookmark_id")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="playlist_id")
	private Playlist playlist;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	public void setUser(User user) {
		this.user=user;
		user.getBookmarks().add(this);
	}
	
	public void setPlaylist(Playlist playlist) {
		this.playlist=playlist;
	}
	
	
	
}
