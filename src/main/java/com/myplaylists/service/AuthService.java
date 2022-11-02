package com.myplaylists.service;

import com.myplaylists.client.GoogleClient;
import com.myplaylists.client.KakaoClient;
import com.myplaylists.domain.User;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.dto.oauth.GoogleOauthDto;
import com.myplaylists.dto.oauth.KakaoOauthDto;
import com.myplaylists.dto.oauth.OauthDto;
import com.myplaylists.exception.BadRequestException;
import com.myplaylists.exception.SignupRequiredException;
import com.myplaylists.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final KakaoClient kakaoClient;
    private final GoogleClient googleClient;

    /**
     * 내플리스 회원가입 처리
     */
    public LoginUser signup(OauthDto oauthDto) {
        if (!StringUtils.hasText(oauthDto.getEmail())) {
            throw new BadRequestException("이메일은 필수입니다. 카카오로 로그인 시 이메일 제공에 동의하지 않았다면 카카오 계정 > 연결된 서비스 관리 > 내플리스 > 연결 끊기 후 이메일 제공에 동의해주세요.");
        }

        User user = userRepository.save(User.Companion.of(oauthDto));
        return new LoginUser(user);
    }

    /**
     * 내플리스 로그인 처리
     */
    public LoginUser login(OauthDto oauthDto) {
        User user = authenticate(oauthDto);
        return new LoginUser(user);
    }

    /**
     * 1. 존재하지 않는 이메일이면 회원가입 요구
     * 2. 존재하는 이메일이면 OAUTH 타입에 따라 처리
     *   2-1. 같은 OAUTH 타입이 이미 존재하면 최신 정보로 업데이트 후 로그인 처리
     *   2-2. 새로운 OAUTH 타입이라면 회원가입 요구
     */
    private User authenticate(OauthDto oauthDto) {
        List<User> users = userRepository.findAllByEmail(oauthDto.getEmail());
        if (CollectionUtils.isEmpty(users)) {
            throw new SignupRequiredException();
        }

        return users.stream()
                .filter(e -> e.getOauthType() == oauthDto.getOauthType())
                .findAny()
                .map(e -> e.updateName(oauthDto.getName()))
                .orElseThrow(() -> { throw new SignupRequiredException(); });
    }

    /**
     * 내플리스 로그인을 위해 필요한 Oauth 객체를 카카오 로그인 정보로부터 가져온다.
     */
    public OauthDto getKakaoUserInfo(String code) {
        KakaoOauthDto kakaoUserInfo = kakaoClient.getKakaoUserInfo(code);
        return kakaoUserInfo.toOauthDto();
    }

    /**
     * 내플리스 로그인을 위해 필요한 Oauth 객체를 구글 로그인 정보로부터 가져온다.
     */
    public OauthDto getGoogleUserInfo(String code) {
        GoogleOauthDto googleUserInfo = googleClient.getGoogleUserInfo(code);
        return googleUserInfo.toOauthDto();
    }
}
