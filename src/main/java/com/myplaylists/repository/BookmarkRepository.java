package com.myplaylists.repository;

import java.util.Optional;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myplaylists.domain.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	Page<Bookmark> findByUserId(Pageable pageable, Long userId);
	Optional<Bookmark> findByUserIdAndPlaylistId(Long userId, Long playlistId); // TODO 테이블 JOIN 성능 체크하기 (QueryDSL 혹은 JPQL 적용해서 불필요한 JOIN은 제거할 수 있어보임)
	Optional<Bookmark> findAllByUserId(Long userId);

}
