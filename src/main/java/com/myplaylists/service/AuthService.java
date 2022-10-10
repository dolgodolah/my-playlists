package com.myplaylists.service;

import com.myplaylists.client.KakaoClient;
import com.myplaylists.domain.User;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.dto.oauth.KakaoOauthDto;
import com.myplaylists.dto.oauth.OauthDto;
import com.myplaylists.exception.InvalidEmailException;
import com.myplaylists.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final KakaoClient kakaoClient;

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
            throw new InvalidEmailException();
        }

        // TODO AuthService.kt 으로 리팩토링해서 확장함수 사용하자.
        User user = userRepository.findByEmail(oauthDto.getEmail())
                .map(entity -> {
                    if (oauthDto.getOauthType() == entity.getOauthType()) {
                        return entity.updateName(oauthDto.getName());
                    }

                    return oauthDto.toEntity();
                })
                .orElse(oauthDto.toEntity());

        return userRepository.save(user);
    }

    /**
     * 내플리스 로그인을 위해 필요한 Oauth 객체를 카카오 로그인 정보로부터 가져온다.
     */
    public OauthDto getKakaoUserInfo(String code) {
        KakaoOauthDto kakaoAccount = kakaoClient.getKakaoUserInfo(code);
        return kakaoAccount.toOauthDto();
    }
}
