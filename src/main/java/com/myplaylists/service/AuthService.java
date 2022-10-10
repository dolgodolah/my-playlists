package com.myplaylists.service;

import com.myplaylists.client.KakaoClient;
import com.myplaylists.domain.User;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.dto.oauth.KakaoAccount;
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
    public LoginUser authenticate(OauthRequest oauthRequest) {
        User user = signUpOrLogin(oauthRequest);
        return new LoginUser(user);
    }

    /**
     * 이미 존재하는 이메일이면 최신 정보로 업데이트 후 로그인 처리,
     * 처음 로그인하는 이메일이면 회원 가입 후 로그인 처리
     */
    private User signUpOrLogin(OauthRequest oauthRequest) {
        if (!StringUtils.hasText(oauthRequest.getEmail())) {
            throw new InvalidEmailException();
        }

        User user = userRepository.findByEmail(oauthRequest.getEmail())
                .map(entity -> entity.updateName(oauthRequest.getName()))
                .orElse(oauthRequest.toEntity());

        return userRepository.save(user);
    }


    /**
     * 내플리스 로그인을 위해 필요한 Oauth 객체를 카카오 로그인 정보로부터 가져온다.
     */
    public KakaoAccount getKakaoUserInfo(String code) {
        return kakaoClient.getKakaoUserInfo(code);
    }
}
