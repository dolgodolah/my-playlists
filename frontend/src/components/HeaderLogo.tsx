import {Link} from "react-router-dom";
import {Icon} from "@iconify/react";

const HeaderLogo = () => {
  return (
    <>
      <div className="logo__container">
        <Link to="/playlist">내플리스</Link>
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