package com.myplaylists.service;

import com.myplaylists.client.GoogleClient;
import com.myplaylists.client.KakaoClient;
import com.myplaylists.domain.User;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.dto.oauth.GoogleOauthDto;
import com.myplaylists.dto.oauth.KakaoOauthDto;
import com.myplaylists.dto.oauth.OauthDto;
import com.myplaylists.exception.BadRequestException;
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
     * 내플리스 로그인 처리
     */
    public LoginUser authenticate(OauthDto oauthDto) {
        User user = signUpOrLogin(oauthDto);
        return new LoginUser(user);
    }

    /**
     * 이미 존재하는 이메일이면 최신 정보로 업데이트 후 로그인 처리,
     * 처음 로그인하는 이메일이면 회원 가입 후 로그인 처리
     */
    private User signUpOrLogin(OauthDto oauthDto) {
        if (!StringUtils.hasText(oauthDto.getEmail())) {
            throw new BadRequestException("이메일은 필수입니다. 카카오로 로그인 시 이메일 제공에 동의하지 않았다면 카카오 계정 > 연결된 서비스 관리 > 내플리스 > 연결 끊기 후 이메일 제공에 동의해주세요.");
        }

        List<User> users = userRepository.findAllByEmail(oauthDto.getEmail());
        if (CollectionUtils.isEmpty(users)) {
            return userRepository.save(oauthDto.toEntity());
        }

        User user = users.stream()
                .filter(e -> e.getOauthType() == oauthDto.getOauthType())
                .findAny()
                .map(e -> e.updateName(oauthDto.getName()))
                .orElse(oauthDto.toEntity());

        return userRepository.save(user);
    }

    /**
     * 내플리스 로그인을 위해 필요한 Oauth 객체를 카카오 로그인 정보로부터 가져온다.
     */
    public OauthDto getKakaoUserInfo(String code) {
        KakaoOauthDto kakaoUserInfo = kakaoClient.getKakaoUserInfo(code);
        return kakaoUserInfo.toOauthDto();
    }

    public OauthDto getGoogleUserInfo(String code) {
        GoogleOauthDto googleUserInfo = googleClient.getGoogleUserInfo(code);
        return googleUserInfo.toOauthDto();
    }
}
