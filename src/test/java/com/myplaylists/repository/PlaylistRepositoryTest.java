package com.myplaylists.repository;

import com.myplaylists.domain.Playlist;
import com.myplaylists.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    void savePlaylist() {
        user = User.builder()
                .name(USER_NAME)
                .nickname(USER_NICKNAME)
                .email(USER_EMAIL)
                .build();
        userRepository.save(user);
    }

    @DisplayName("해당 유저의 모든 플레이리스트를 업데이트 최신 순으로 조회")
    @Test
    void findAllByUserOrderByUpdatedDateDesc() {
        for (int i = 0; i < MAX_SIZE; i++) {
            playlistRepository.save(
                    Playlist.builder()
                            .user(user)
                            .title(PLAYLIST_TITLE)
                            .description(PLAYLIST_DESCRIPTION)
                            .visibility(PUBLIC)
                            .build());
        }

        Page<Playlist> result = playlistRepository.findAllByUserOrderByUpdatedDateDesc(FIRST_PAGE, user);
        Playlist first = result.stream().findFirst().get();
        Playlist last = result.stream().skip(MAX_SIZE - 1).findFirst().get();

        assertThat(first.getUpdatedDate()).isAfter(last.getUpdatedDate());
        assertThat(first.getUser()).isEqualTo(user);
        assertThat(first.getTitle()).isEqualTo(PLAYLIST_TITLE);
        assertThat(first.getDescription()).isEqualTo(PLAYLIST_DESCRIPTION);
        assertThat(first.isVisibility()).isTrue();
    }

    @DisplayName("검색어에 포함되는 해당 유저의 플레이리스트 조회")
    @ParameterizedTest
    @ValueSource(strings = {"코딩", "코딩하면서", "노래", "듣기 좋은 노래", "코딩하면서 듣기 좋은 노래"})
    void findByTitleContainingAndUserId(String input) {
        playlistRepository.save(
                Playlist.builder()
                        .user(user)
                        .title("코딩하면서 듣기 좋은 노래")
                        .description(PLAYLIST_DESCRIPTION)
                        .visibility(PUBLIC)
                        .build());


        Optional<Playlist> result = playlistRepository.findByTitleContainingAndUserId(FIRST_PAGE, input, user.getId())
                .stream().findAny();

        assertThat(result).isNotEmpty();
        assertThat(result.get().getTitle()).isEqualTo("코딩하면서 듣기 좋은 노래");
        assertThat(result.get().getUser()).isEqualTo(user);
        assertThat(result.get().getDescription()).isEqualTo(PLAYLIST_DESCRIPTION);
        assertThat(result.get().isVisibility()).isTrue();
    }

    @DisplayName("검색어에 포함되는 모든 유저의 플레이리스트 조회")
    @ParameterizedTest
    @ValueSource(strings = {"코딩", "코딩하면서", "노래", "듣기 좋은 노래", "코딩하면서 듣기 좋은 노래"})
    void findByTitleContaining(String input) {
        playlistRepository.save(
                Playlist.builder()
                        .user(user)
                        .title("코딩하면서 듣기 좋은 노래")
                        .description(PLAYLIST_DESCRIPTION)
                        .visibility(PUBLIC)
                        .build());

        User other = userRepository.save(User.builder()
                .name("심청이")
                .nickname("심봉사")
                .email("test@myplaylists.test")
                .build());

        playlistRepository.save(
                Playlist.builder()
                        .user(other)
                        .title("코딩하면서 듣기 좋은 노래")
                        .description(PLAYLIST_DESCRIPTION)
                        .visibility(PUBLIC)
                        .build());

        Page<Playlist> result = playlistRepository.findByTitleContaining(FIRST_PAGE, input);

        assertThat(result.stream().count()).isEqualTo(2L);
        result.stream().forEach(playlist -> {
            assertThat(playlist.getTitle()).isEqualTo("코딩하면서 듣기 좋은 노래");
            assertThat(playlist.getUser()).isIn(user, other);
            assertThat(playlist.getDescription()).isEqualTo(PLAYLIST_DESCRIPTION);
            assertThat(playlist.isVisibility()).isTrue();
        });


    }
}