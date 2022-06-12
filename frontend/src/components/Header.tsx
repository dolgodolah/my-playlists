import {Link, useSearchParams} from "react-router-dom";
import {Icon} from "@iconify/react";
import React, {useCallback, useState} from "react";
import SearchType from "../shared/SearchType";

interface HeaderProps {
  type: SearchType
}

const Header = ({ type }: HeaderProps) => {
  const [params] = useSearchParams();
  const [keyword, setKeyword] = useState(params.get('keyword') || "");
  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  return (
    <div className="header__container">
      <div className="logo__container">
        <Link to="/">내플리스</Link>
      </div>
      <div className="button__container--header">
        <Link to="/playlist/add" className="add__button--header">
          <Icon icon="ic:baseline-playlist-add" />
        </Link>
      </div>

      <div className="search__container">
        <form method="get" action="/search">
          <input
            type="text"
            placeholder={type === SearchType.PLAYLIST ? "플레이리스트 검색" : "노래 검색"}
            name="keyword"
            className="search__input--header"
            value={keyword}
            onChange={onChangeKeyword}
          />
        </form>
      </div>
    </div>
  )
}

export default Header;