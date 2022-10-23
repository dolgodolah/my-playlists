package com.myplaylists.repository;

import com.myplaylists.domain.OauthType;
import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PlaylistRepositoryTest {

    private static final String USER_NAME = "홍길동";
    private static final String USER_NICKNAME = "동에번쩍";
    private static final String USER_EMAIL = "test@test.test";
    private static final String PLAYLIST_TITLE = "플레이리스트 타이틀";
    private static final String PLAYLIST_DESCRIPTION = "플레이리스트 설명";
    private static final boolean PUBLIC = true;
    private static final int MAX_SIZE = 6;
    private static final Pageable FIRST_PAGE = PageRequest.of(0, MAX_SIZE);

    @Autowired PlaylistRepository playlistRepository;
    @Autowired UserRepository userRepository;

    private User user;

    @BeforeEach
    void saveUser() {
        user = new User(null, USER_NAME, USER_NICKNAME, USER_EMAIL, OauthType.KAKAO);
        userRepository.save(user);
    }

    @DisplayName("내 플레이리스트 검색")
    @ParameterizedTest
    @ValueSource(strings = {"코딩", "코딩하면서", "노래", "듣기 좋은 노래", "코딩하면서 듣기 좋은 노래"})
    void findAllByUserIdAndTitleContaining(String input) {
        // set user's playlist
        Playlist playlist = new Playlist(null, user, "코딩하면서 듣기 좋은 노래", PLAYLIST_DESCRIPTION, PUBLIC);
        playlistRepository.save(playlist);

        // search my playlist
        Optional<Playlist> result = playlistRepository.findAllByUserIdAndTitleContaining(user.getId(), input)
                .stream().findAny();

        assertThat(result).isNotEmpty();
        assertThat(result.get().getTitle()).isEqualTo("코딩하면서 듣기 좋은 노래");
        assertThat(result.get().getUser()).isEqualTo(user);
        assertThat(result.get().getDescription()).isEqualTo(PLAYLIST_DESCRIPTION);
        assertThat(result.get().getVisibility()).isTrue();
    }

    @DisplayName("모든 플레이리스트 검색")
    @ParameterizedTest
    @ValueSource(strings = {"코딩", "코딩하면서", "노래", "듣기 좋은 노래", "코딩하면서 듣기 좋은 노래"})
    void findByTitleContaining(String input) {
        // set user's playlist
        playlistRepository.save(new Playlist(null, user, "코딩하면서 듣기 좋은 노래", PLAYLIST_DESCRIPTION, PUBLIC));

        // set other user's playlist
        User other = userRepository.save(new User(null, "심청이", "심봉사", "test@myplaylists.test", OauthType.KAKAO));
        playlistRepository.save(new Playlist(null, other, "코딩하면서 듣기 좋은 노래", PLAYLIST_DESCRIPTION, PUBLIC));

        // search all playlist
        Page<Playlist> result = playlistRepository.findByTitleContaining(FIRST_PAGE, input);

        assertThat(result.get().count()).isEqualTo(2);
        result.stream().forEach(playlist -> {
            assertThat(playlist.getTitle()).isEqualTo("코딩하면서 듣기 좋은 노래");
            assertThat(playlist.getUser()).isIn(user, other);
            assertThat(playlist.getDescription()).isEqualTo(PLAYLIST_DESCRIPTION);
            assertThat(playlist.getVisibility()).isTrue();
        });
    }
}