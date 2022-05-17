import { useNavigate } from "react-router-dom";

const ProfileMenu = () => {
  const navigate = useNavigate();

  const goToMe = () => {
    navigate("/me");
  };
  const logout = () => {
    console.log("logout");
    // 로그아웃 로직
  };
  return (
    <div className="profile-menu__container">
      <span className="profile-menu__span" onClick={goToMe}>
        내 정보
      </span>
      <span className="profile-menu__span--logout" onClick={logout}>
        로그아웃
      </span>
    </div>
  );
};

export default ProfileMenu;
