import { Link } from "react-router-dom";
import axios from "axios";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";
import {useState} from "react";

const ProfileMenu = () => {
  const [isGuest] = useState(!sessionStorage.getItem("login_at"));
  const logout = () => {
    axios
      .post("/logout")
      .then((res) => {
        const response = res.data
        switch (response.statusCode) {
          case StatusCode.OK:
            sessionStorage.removeItem("login_at")
            goToLogin();
            break;
          default:
            alertError(response)
        }
      })
  };

  const goToLogin = () => {
    window.location.href = "/login";
  }

  return (
    <div className="profile-menu__container">
      {isGuest ?
        <span className="profile-menu__span--logout" onClick={goToLogin}>
            로그인
        </span> :
        <>
          <Link to="/me" className="profile-menu__span">
            내 정보
          </Link>
          <span className="profile-menu__span--logout" onClick={logout}>
            로그아웃
          </span>
        </>
      }
    </div>
  );
};

export default ProfileMenu;
