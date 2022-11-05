import {Link} from "react-router-dom";
import {Icon} from "@iconify/react";
import {useState} from "react";

const HeaderLogo = () => {
  const [isGuest] = useState(!sessionStorage.getItem("login_at"));
  return (
    <>
      <div className="logo__container">
        {isGuest ? <Link to="/playlist" state={{ page: "allPlaylist" }}>내플리스</Link> : <Link to="/playlist">내플리스</Link>}
      </div>
      <div className="button__container--header">
        <Link to="/playlist/add" className="add__button--header">
          <Icon icon="ic:baseline-playlist-add" />
        </Link>
      </div>
    </>
  )
}

export default HeaderLogo;