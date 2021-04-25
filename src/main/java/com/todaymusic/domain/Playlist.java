package com.todaymusic.domain;

import java.time.LocalDateTime;
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	@OneToMany(mappedBy="playlist")
	private List<Song> songs = new ArrayList<>();
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false)
	private boolean visibility;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;

	
	//==연관관계 편의 메서드==//
	public void setMember(Member member) {
		this.member = member;
		member.getPlaylists().add(this);
	}
	
	public void addSong(Song song) {
		songs.add(song);
		song.setPlaylist(this);
	}
	
	@Override
	public String toString() {
		return "Playlist [id=" + id + ", member=" + member + ", title=" + title + ", visibility=" + visibility
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}





}
