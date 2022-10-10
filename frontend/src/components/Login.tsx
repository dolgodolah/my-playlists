import {Icon} from "@iconify/react";
import {useEffect} from "react";
import axios from "axios";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";
import {useSearchParams} from "react-router-dom";

export const LoginMenu = () => {
  const kakaoLogin = () => {
    window.location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.REACT_APP_KAKAO_REST_KEY}&redirect_uri=http://localhost:3000/login/kakao&response_type=code`;
  };

  const googleLogin = () => {
  }

  return (
    <div className="login__container">
      <span className="login__span">환영합니다!</span>
      <button className="login__button" onClick={googleLogin} name="google">
        <div className="icon__container">
          <Icon icon="akar-icons:google-fill" />
        </div>
        <span className="button__span">구글로 시작하기</span>
      </button>
      <button className="login__button" onClick={kakaoLogin} name="kakao">
        <div className="icon__container">
          <Icon icon="ri:kakao-talk-fill" />
        </div>
        <span className="button__span">카카오로 시작하기</span>
      </button>
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
        switch (res.status) {
          case StatusCode.OK:
            authenticate(res.data);
            break;
          default:
            alertError(res.data);
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
          window.location.href = "/";
          break;
        case StatusCode.INVALID_EMAIL:
          alertError(response)
          window.location.href = "/login";
          break;
      }
    })
}