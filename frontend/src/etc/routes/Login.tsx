import { Icon } from "@iconify/react";
import { useEffect } from "react";

const Login = () => {
  useEffect(() => {
    window.Kakao.init(process.env.REACT_APP_KAKAO_SDK_KEY);
  }, []);

  const onSocialClick = () => {
    window.Kakao.Auth.login({
      success: function (result: any) {
        fetch("/login/kakao", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            accessToken: result.access_token,
          }),
        }).then((res) => {
          console.log(res);
          //  메인페이지 redirect
        });
      },
      fail: function (error: any) {
        console.log(error);
      },
    });
  };

  return (
    <>
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
    </>
  );
};

export default Login;
