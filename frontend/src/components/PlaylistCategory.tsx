import classNames from "classnames";
import { Link } from "react-router-dom";
import { PlaylistCategoryProps } from "../shared/Props";

const PlaylistCategory = ({ pathname }: PlaylistCategoryProps) => {
  return (
    <div className="category__container">
      <Link
        to="/"
        className={classNames("category__link", { isBright: pathname === "/" })}
      >
        내 플레이리스트
      </Link>
      <Link
        to="/all"
        className={classNames("category__link", {
          isBright: pathname === "/all",
        })}
      >
        모든 플레이리스트
      </Link>
      <Link
        to="/bookmarks"
        className={classNames("category__link", {
          isBright: pathname === "/bookmarks",
        })}
      >
        즐겨찾기
      </Link>
    </div>
  );
};

export default PlaylistCategory;
