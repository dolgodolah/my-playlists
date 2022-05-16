import { useNavigate } from "react-router-dom";

interface UserProps {
  user: Record<string, any>;
}

const ProfileMenu = ({ user }: UserProps) => {
  const navigate = useNavigate();

  const onUserClick = () => {
    navigate("/me");
  };
  const logout = () => {
    console.log("logout");
    // 로그아웃 로직
  };
  return (
    <div className="profile-menu__container">
      <span className="profile-menu__span" onClick={onUserClick}>
        {user.nickname}
      </span>
      <span className="profile-menu__span--logout" onClick={logout}>
        로그아웃
      </span>
    </div>
  );
};

export default ProfileMenu;
