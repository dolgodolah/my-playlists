import classNames from "classnames";
import React from "react";

export type CategoryType = "my-playlists" | "all-playlists" | "bookmarks"

interface CategoryProps {
  page: string
  getPlaylists: (pageType: CategoryType) => void
}

const Category = ({ page, getPlaylists }: CategoryProps) => {
  const currentCategory = page || "my-playlists"

  return (
    <div className="category__container">
      <a
        href="#"
        className={classNames("category__link", {
          isBright: currentCategory === "my-playlists"
        })}
        onClick={() => getPlaylists("my-playlists")}
      >
        내 플레이리스트
      </a>
      <a
        href="#"
        className={classNames("category__link", {
          isBright: currentCategory === "all-playlists",
        })}
        onClick={() => getPlaylists("all-playlists")}
      >
        모든 플레이리스트
      </a>
      <a
        href="#"
        className={classNames("category__link", {
          isBright: currentCategory === "bookmarks",
        })}
        onClick={() => getPlaylists("bookmarks")}
      >
        즐겨찾기
      </a>
    </div>
  );
};

export default Category;
