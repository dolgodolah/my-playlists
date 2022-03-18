package com.myplaylists.service;

import com.myplaylists.domain.User;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.dto.auth.OauthRequest;
import com.myplaylists.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public LoginUser authenticate(OauthRequest oauthRequest) {
        User user = signUpOrLogin(oauthRequest);
        return new LoginUser(user);
    }

    /**
     * 이미 존재하는 이메일이면 최신 정보로 업데이트 후 로그인 처리,
     * 처음 로그인하는 이메일이면 회원 가입 후 로그인 처리
     */
    private User signUpOrLogin(OauthRequest oauthRequest) {
        User user = userRepository.findByEmail(oauthRequest.getEmail())
                .map(entity -> entity.updateName(oauthRequest.getName()))
                .orElse(oauthRequest.toEntity());

        return userRepository.save(user);
    }
}
