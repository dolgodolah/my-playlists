package com.myplaylists.service;

import com.myplaylists.domain.User;
import com.myplaylists.dto.auth.LoginUser;
import com.myplaylists.dto.auth.OauthRequest;
import com.myplaylists.exception.InvalidEmailException;
import com.myplaylists.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
        if (!StringUtils.hasText(oauthRequest.getEmail())) {
            throw new InvalidEmailException("이메일은 필수입니다. 카카오로 로그인 시 이메일 제공에 동의하지 않았다면 카카오 계정 > 연결된 서비스 관리 > 내플리스 > 연결 끊기 후 이메일 제공에 동의해주세요.");
        }

        User user = userRepository.findByEmail(oauthRequest.getEmail())
                .map(entity -> entity.updateName(oauthRequest.getName()))
                .orElse(oauthRequest.toEntity());

        return userRepository.save(user);
    }
}
