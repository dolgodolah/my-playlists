import { Icon } from "@iconify/react";
import {Link, useSearchParams} from "react-router-dom";
import { PlaylistProps } from "../shared/Props";
import React, {useCallback, useEffect, useState} from "react";
import axios from "axios";

interface PageProps {
  page: string;
}

const Playlist = ({ page }: PageProps) => {
  const [params] = useSearchParams();
  const [playlists, setPlaylists] = useState([]) as Array<any>;
  const [keyword, setKeyword] = useState(params.get('keyword') || "");


  const onChangeKeyword = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  useEffect(() => {
    switch (page) {
      case "myPlaylist":
        axios.get("/my_playlists").then( (res) => {
            const playlists: Array<any> = res.data.playlists;
            setPlaylists(playlists);
        }).catch((error) => {
          console.log(error);
        });
        break;
      case "allPlaylist":
        axios.get("/all_playlists").then( (res) => {
          const playlists: Array<any> = res.data.playlists;
          setPlaylists(playlists);
        }).catch((error) => {
          console.log(error);
        });
        break;
      case "bookmarks":
        axios.get("/bookmarks").then( (res) => {
          const playlists: Array<any> = res.data.playlists;
          setPlaylists(playlists);
        }).catch((error) => {
          console.log(error);
        });
        break;
      case "search":
        axios.get("/playlist/search", { params: { keyword: keyword } }).then((res) => {
          const playlists: Array<any> = res.data.playlists;
          setPlaylists(playlists);
        }).catch((error) => {
          console.log(error);
        });
        break;
    }
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
            <span className="hidden-background__containe__span">
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

export default Playlist;
