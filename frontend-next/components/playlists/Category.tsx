import classNames from "classnames";
import React from "react";

export type CategoryType = "my-playlists" | "all-playlists" | "bookmarks"

interface CategoryProps {
  category: CategoryType
  getPlaylists: (category: CategoryType) => void
}

const Category = ({ category, getPlaylists }: CategoryProps) => {

  return (
    <div className="category__container">
      <a
        href="#"
        className={classNames("category__link", {
          isBright: category === "my-playlists"
        })}
        onClick={() => getPlaylists("my-playlists")}
      >
        내 플레이리스트
      </a>
      <a
        href="#"
        className={classNames("category__link", {
          isBright: category === "all-playlists",
        })}
        onClick={() => getPlaylists("all-playlists")}
      >
        모든 플레이리스트
      </a>
      <a
        href="#"
        className={classNames("category__link", {
          isBright: category === "bookmarks",
        })}
        onClick={() => getPlaylists("bookmarks")}
      >
        즐겨찾기
      </a>
    </div>
  );
};

export default Category;
