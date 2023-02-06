import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";
import React from "react";
import {PageContext} from "./hoc/withPageContext";
import useClient from "./hooks/useClient";

const ProfileMenu = () => {
  const client = useClient()
  const context = { ...React.useContext(PageContext) }
  const logout = async () => {
    const res = await client.post("/logout")
    switch (res.statusCode) {
      case StatusCode.OK:
        goToLogin()
        break
      default:
        alertError(res)
        break
    }
  };

  const goToLogin = () => {
    location.href = "/login"
  }

  const goToMe = () => {
    location.href = "/me"
  }

  return (
    <div className="profile-menu__container">
      {context.isGuest ?
        <span className="profile-menu__span--logout" onClick={goToLogin}>로그인</span>
        : <>
          <span className="profile-menu__span" onClick={goToMe}>내 정보</span>
          <span className="profile-menu__span--logout" onClick={logout}>로그아웃</span>
        </>
      }
    </div>
  );
};

export default ProfileMenu;
