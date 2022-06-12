import {Icon} from "@iconify/react";
import {useEffect} from "react";
import axios from "axios";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";

const Login = () => {
  useEffect(() => {
    window.Kakao.init(process.env.REACT_APP_KAKAO_SDK_KEY);
  }, []);

  const login = (res: any) => {
    axios
      .post("/login/kakao", {
        email: res.kakao_account.email,
        name: res.kakao_account.profile.nickname,
        attributes: res,
      })
      .then((res) => {
        const response = res.data
        switch (response.statusCode) {
          case StatusCode.OK:
            window.location.href = "/";
            break;
          case StatusCode.INVALID_EMAIL:
            alertError(response.message)
            break;
        }
      })
  };

  const kakaoLogin = () => {
    window.Kakao.Auth.login({
      success: () => {
        window.Kakao.API.request({
          url: "/v2/user/me",
          success: (response: any) => {
            login(response);
          },
          fail: (response: any) => {
            console.log(response);
          },
        });
      },
      fail: (error: any) => {
        console.log(error);
      },
    });
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

export default Login;
