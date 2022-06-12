import { Icon } from "@iconify/react";
import {Link, useSearchParams} from "react-router-dom";
import { PlaylistProps } from "../shared/Props";
import React, {useCallback, useEffect, useState} from "react";
import axios from "axios";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";
import HeaderLogo from "./HeaderLogo";

interface PageProps {
  page: string;
}

const Playlists = ({ page }: PageProps) => {
  const [params] = useSearchParams();
  const [playlists, setPlaylists] = useState([]);
  const [keyword, setKeyword] = useState(params.get('keyword') || "");

  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  const onPressEnter = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      window.location.href = "/search?keyword=" + keyword;
    }
  }

  useEffect(() => {
    switch (page) {
      case "myPlaylist":
        axios.get("/my_playlists").then( (res) => {
          const response = res.data
          switch (response.statusCode) {
            case StatusCode.OK:
              setPlaylists(response.playlists);
              break;
            default:
              alertError(response.message)
              break;
          }
        })
        break;
      case "allPlaylist":
        axios.get("/all_playlists").then( (res) => {
          const response = res.data
          switch (response.statusCode) {
            case StatusCode.OK:
              setPlaylists(response.playlists);
              break;
            default:
              alertError(response.message)
              break;
          }
        })
        break;
      case "bookmarks":
        axios.get("/bookmarks").then( (res) => {
          const response = res.data
          switch (response.statusCode) {
            case StatusCode.OK:
              setPlaylists(response.playlists);
              break;
            default:
              alertError(response.message)
              break;
          }
        })
        break;
      case "search":
        axios.get("/playlist/search", { params: { keyword: keyword } }).then((res) => {
          const response = res.data
          switch (response.statusCode) {
            case StatusCode.OK:
              setPlaylists(response.playlists);
              break;
            default:
              alertError(response.message)
              break;
          }
        })
        break;
    }
  }, [page]);

  return (
    <>
      <div className="header__container">
        <HeaderLogo />
        <div className="search__container">
          <input
            type="text"
            placeholder="플레이리스트 검색"
            className="search__input--header"
            value={keyword}
            onChange={onChangeKeyword}
            onKeyPress={onPressEnter}
          />
        </div>
      </div>

      <div className="lists__container">
        {playlists.map((playlist: PlaylistProps) => (
          <Link
            key={playlist.playlistId}
            to="/playlist"
            state={{
              page: "showSongs",
              playlist: playlist
            }}
            className="playlist__link"
          >
            <div className="playlist__container">
              <div className="playlist__container--left">
                <span className="playlist__span--title">{playlist.title}</span>
                <div>
              <span className="playlist__span--updatedDate">
                {playlist.updatedDate}
              </span>
                  <span className="playlist__span--author">{playlist.author}</span>
                </div>
              </div>
              <div className="playlist__container--right">
                <span className="playlist__span--amount">{playlist.songCount}</span>
                <div className="playlist-icon__container">
                  <Icon icon="bxs:playlist" />
                </div>
              </div>
              <div className="hidden-background__container">
                <span className="hidden-background__container__span">
                  <Icon icon="codicon:debug-start" />
                </span>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </>
  )
};

export default Playlists;
