import {LoginMenu, KakaoLogin, GoogleLogin} from "../components/Login"

interface LoginProps {
  loginType?: string;
}

const login = ({loginType}: LoginProps) => {
  switch (loginType) {
    case "kakao":
      return (
        <KakaoLogin />
      );
    case "google":
      return (
        <GoogleLogin />
      );
    default:
      return (
        <LoginMenu />
      )
  }
};

export default login;
