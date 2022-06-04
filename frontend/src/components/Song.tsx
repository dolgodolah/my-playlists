import { Icon } from "@iconify/react";
import { useCallback, useState } from "react";
import { Link, useSearchParams } from "react-router-dom";
import { songs } from "../test/user";

const Song = () => {
  const [keyword, setKeyword] = useState("");
  const [params, setParams] = useSearchParams();
  // const keyword = params.get('keyword');

  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);
  return (
    <>
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
              placeholder="플레이리스트 검색"
              name="keyword"
              className="search__input--header"
              value={keyword}
              onChange={onChangeKeyword}
            />
          </form>
        </div>
      </div>
      <div className="lists__container">
        {songs?.map((song) => (
          <Link
            to="/playlist"
            state={{
              page: "playSongs",
              song: song,
            }}
          >
            <div className="song__container">
              <span className="song__span--title">{song.title}</span>
              <div>
                <span className="song__span--createdDate">
                  {song.createdDate}
                </span>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </>
  );
};

export default Song;
