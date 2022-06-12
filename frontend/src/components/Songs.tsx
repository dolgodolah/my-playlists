import { Icon } from "@iconify/react";
import {useCallback, useEffect, useState} from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";
import {PlaylistProps, SongProps} from "../shared/Props";
import HeaderLogo from "./HeaderLogo";

interface SongsProps {
  playlist: PlaylistProps;
}

const Songs = ({ playlist }: SongsProps) => {
  const [keyword, setKeyword] = useState("");
  const [songs, setSongs] = useState([]);

  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  useEffect(() => {
    axios.get("/songs", { params: { playlistId: playlist.playlistId } }).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          setSongs(response.songs);
          break;
        default:
          alertError(response.message)
          break;
      }
    })
  }, []);

  return (
    <>
      <div className="header__container">
        <HeaderLogo />
        <div className="search__container">
          <form method="get" action="/search">
            <input
              type="text"
              placeholder="노래 검색"
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
              page: "playSong",
              playlist: playlist,
              playedSong: song,
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

export default Songs;
