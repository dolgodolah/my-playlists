import {Icon} from "@iconify/react";
import {useEffect} from "react";
import axios from "axios";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";
import {Link, useSearchParams} from "react-router-dom";

export const LoginMenu = () => {
  const kakaoLogin = () => {
    window.location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.REACT_APP_KAKAO_REST_KEY}&redirect_uri=http://localhost:3000/login/kakao&response_type=code`;
  };

  const googleLogin = () => {
    window.location.href = "https://accounts.google.com/o/oauth2/v2/auth?" +
      "scope=https%3A//www.googleapis.com/auth/userinfo.profile https%3A//www.googleapis.com/auth/userinfo.email&" +
      "access_type=offline&" +
      "response_type=code&" +
      "redirect_uri=http%3A//localhost:3000/login/google&" +
      "client_id=" + process.env.REACT_APP_GOOGLE_REST_KEY
  }

  const guestLogin = () => {
    alert("비회원의 경우 최대 20개의 플레이리스트를 즐기실 수 있습니다. 더 많은 기능을 이용하시려면 간편 로그인을 통해 내플리스를 이용하세요!")
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
      <Link to="/playlist" state={{ page: "allPlaylist" }} className="login__button" onClick={guestLogin}>
        <div className="icon__container">
          <Icon icon="carbon:user-avatar-filled-alt" />
        </div>
        <span className="button__span">비회원으로 시작하기</span>
      </Link>
    </div>
  )
};

export const KakaoLogin = () => {
  const [params] = useSearchParams();
  useEffect(() => {
    axios
      .get("/login/kakao", {
        params: {
          code: params.get("code")
        }
      })
      .then((res) => {
        const response = res.data;
        switch (response.statusCode) {
          case StatusCode.OK:
            authenticate(response);
            break;
          default:
            alertError(response);
            break;
        }
      })
  })

  return null;
}

export const GoogleLogin = () => {
  const [params] = useSearchParams();
  useEffect(() => {
    axios
      .get("/login/google", {
        params: {
          code: params.get("code")
        }
      })
      .then((res) => {
        const response = res.data
        switch (response.statusCode) {
          case StatusCode.OK:
            authenticate(response)
            break;
          default:
            alertError(response)
            break;
        }
      })
  })
  return null;
}

/**
 * 내플리스 인증 처리
 * @param oauthRequest
 */
const authenticate = (oauthRequest: any) => {
  axios
    .post("/login", {
      email: oauthRequest.email,
      name: oauthRequest.name,
      oauthType: oauthRequest.oauthType
    })
    .then((res) => {
      const response = res.data
      switch (response.statusCode) {
        case StatusCode.OK:
          sessionStorage.setItem("login_at", Date.now().toString())
          window.location.href = "/";
          break;
        case StatusCode.SIGNUP_REQUIRED:
          signup(oauthRequest)
          break;
      }
    })
}

const signup = (oauthRequest: any) => {
  axios
    .post("/signup", {
      email: oauthRequest.email,
      name: oauthRequest.name,
      oauthType: oauthRequest.oauthType
    })
    .then((res) => {
      const response = res.data
      switch (response.statusCode) {
        case StatusCode.OK:
          window.location.href = "/";
          break;
        case StatusCode.BAD_REQUEST:
          alertError(response)
          window.location.href = "/login";
          break;
      }
    })
}