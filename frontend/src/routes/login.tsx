import { Icon } from "@iconify/react";
import { useEffect } from "react";
import axios from "axios";

const Login = () => {
  useEffect(() => {
    window.Kakao.init(process.env.REACT_APP_KAKAO_SDK_KEY);
  }, []);

  const login = (response: any) => {
    axios
      .post("/login/kakao", {
        email: response.kakao_account.email,
        name: response.kakao_account.profile.nickname,
        attributes: response,
      })
      .then(function (response) {
        window.location.href = "/";
      })
      .catch(function (error) {
        console.log(error);
      });
  };

  const onSocialClick = () => {
    window.Kakao.Auth.login({
      success: function () {
        window.Kakao.API.request({
          url: "/v2/user/me",
          success: function (response: any) {
            login(response);
          },
          fail: function (response: any) {
            console.log(response);
          },
        });
      },
      fail: function (error: any) {
        console.log(error);
      },
    });
  };

  return (
    <div className="login__container">
      <span className="login__span">환영합니다!</span>
      <button className="login__button" onClick={onSocialClick} name="google">
        <div className="icon__container">
          <Icon icon="akar-icons:google-fill" />
        </div>
        <span className="button__span">구글로 시작하기</span>
      </button>
      <button className="login__button" onClick={onSocialClick} name="kakao">
        <div className="icon__container">
          <Icon icon="ri:kakao-talk-fill" />
        </div>
        <span className="button__span">카카오로 시작하기</span>
      </button>
    </div>
  );
};

export default Login;
