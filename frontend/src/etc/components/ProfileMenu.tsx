interface user {
  name: string;
}

const ProfileMenu = (userObj: user) => {
  return (
    <div className="profile-menu__container--footer">
      <span className="user-name__span--footer">{userObj.name}</span>
      <span className="profile-menu__span">로그아웃</span>
    </div>
  );
};

export default ProfileMenu;
