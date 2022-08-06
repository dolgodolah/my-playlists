import { Link } from "react-router-dom";
import axios from "axios";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";

const ProfileMenu = () => {
  const logout = () => {
    axios
      .post("/logout")
      .then((res) => {
        const response = res.data
        switch (response.statusCode) {
          case StatusCode.OK:
            window.location.href = "/login";
            break;
          default:
            alertError(response)
        }
      })
  };
  return (
    <div className="profile-menu__container">
      <Link to="/me" className="profile-menu__span">
        내 정보
      </Link>
      <span className="profile-menu__span--logout" onClick={logout}>
        로그아웃
      </span>
    </div>
  );
};

export default ProfileMenu;
