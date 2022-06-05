import { Icon } from "@iconify/react";
import {useCallback, useEffect, useState} from "react";
import { Link, useSearchParams } from "react-router-dom";
import axios from "axios";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";
import {SongProps} from "../shared/Props";

const Song = ({ playlistId }: any) => {
  const [keyword, setKeyword] = useState("");
  const [songs, setSongs] = useState([]);

  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  useEffect(() => {
    axios.get("/songs", { params: { playlistId: playlistId } }).then((res) => {
      const response = res.data
      switch (response.statusCode) {
        case StatusCode.OK:
          setSongs(response.body.songs);
          break;
        default:
          alertError(response.body)
          break;
      }
    })
  }, []);

  return (
    <>
      {/* TODO: 헤더 컴포넌트 다시 생성 */}
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
        {songs?.map((song: SongProps) => (
          <Link
            key={song.songId}
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
