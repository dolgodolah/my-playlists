package com.todaymusic.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
public class Playlist {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="playlist_id")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy="playlist")
	private List<Song> songs = new ArrayList<>();
	
	@Column
	@Size(max=30, message = "최대 30자까지 가능합니다.")
	@NotBlank(message="플레이리스트 제목을 입력해주세요.")
	private String title;
	
	@Column
	@Size(max=30, message = "최대 30자까지 가능합니다.")
	private String description;
	
	@Column(nullable=false)
	private boolean visibility;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;

	
	//==연관관계 편의 메서드==//
	public void setMember(User user) {
		this.user = user;
		user.getPlaylists().add(this);
	}
	
	public void addSong(Song song) {
		songs.add(song);
		song.setPlaylist(this);
	}
	

}
