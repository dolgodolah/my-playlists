import { Icon } from "@iconify/react";

const Login = () => {
  const onSocialClick = () => {};
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
