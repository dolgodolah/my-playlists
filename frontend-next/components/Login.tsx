import {Icon} from "@iconify/react";

export const LoginMenu = () => {
  const kakaoLogin = () => {
    location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.NEXT_PUBLIC_KAKAO_REST_KEY}&redirect_uri=${process.env.NEXT_PUBLIC_KAKAO_REDIRECT_URI}&response_type=code`;
  };

  const googleLogin = () => {
    location.href = `https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A//www.googleapis.com/auth/userinfo.profile https%3A//www.googleapis.com/auth/userinfo.email&access_type=offline&response_type=code&redirect_uri=${process.env.NEXT_PUBLIC_GOOGLE_REDIRECT_URI}&client_id=${process.env.NEXT_PUBLIC_GOOGLE_REST_KEY}`
  }

  const guestLogin = () => {
    alert("비회원의 경우 최대 20개의 플레이리스트를 즐기실 수 있습니다. 더 많은 기능을 이용하시려면 간편 로그인을 통해 내플리스를 이용하세요!")
    location.href="/playlists"
  }

  return (
    <div className="login__container">
      <span className="login__span">환영합니다!</span>
      <button className="login__button" onClick={googleLogin}>
        <div className="icon__container">
          <Icon icon="akar-icons:google-fill" />
        </div>
        <span className="button__span">구글로 시작하기</span>
      </button>
      <button className="login__button" onClick={kakaoLogin}>
        <div className="icon__container">
          <Icon icon="ri:kakao-talk-fill" />
        </div>
        <span className="button__span">카카오로 시작하기</span>
      </button>
      <button className="login__button" onClick={guestLogin}>
        <div className="icon__container">
          <Icon icon="carbon:user-avatar-filled-alt" />
        </div>
        <span className="button__span">비회원으로 시작하기</span>
      </button>
    </div>
  )
};