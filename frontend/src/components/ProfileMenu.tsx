import { Link } from "react-router-dom";

const ProfileMenu = () => {
  const logout = () => {
    console.log("logout");
    // 로그아웃 로직
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
