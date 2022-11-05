import { PlaylistProps } from "../../shared/Props";
import React, { useEffect, useState } from "react";
import axios from "axios";
import StatusCode from "../../shared/StatusCode";
import alertError from "../../shared/Error";
import Playlist from "./Playlist";
import PlaylistsHeader from "./PlaylistsHeader";
import { useSearch } from "../hooks/useSearch";
import usePageObserver from "../hooks/usePageObserver";

export const MyPlaylists = () => {
  const { keyword, setKeyword, search } = useSearch();
  const [playlists, setPlaylists] = useState([]);

  useEffect(() => {
    axios.get(`/my-playlists`).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          setPlaylists(playlists.concat(response.playlists));
          break;
        default:
          alertError(response);
          break;
      }
    });
  }, []);

  return (
    <>
      <div className="header__container">
        <PlaylistsHeader keyword={keyword} onChangeKeyword={setKeyword} onPressEnter={search} />
      </div>
      <div className="lists__container">
        {playlists.map((playlist: PlaylistProps) => (
          <Playlist key={playlist.playlistId} playlist={playlist} />
        ))}
      </div>
    </>
  );
};

export const AllPlaylists = () => {
  const [playlists, setPlaylists] = useState([]);
  const { keyword, setKeyword, search } = useSearch();
  const { page, setLast: setLastPlaylist } = usePageObserver();

  useEffect(() => {
    axios.get(`/all-playlists?page=${page}`).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          setPlaylists(playlists.concat(response.playlists));
          break;
        default:
          alertError(response);
          break;
      }
    });
  }, [page]);

  return (
    <>
      <div className="header__container">
        <PlaylistsHeader keyword={keyword} onChangeKeyword={setKeyword} onPressEnter={search} />
      </div>
      <div className="lists__container">
        {playlists.map((playlist: PlaylistProps) => (
          <Playlist key={playlist.playlistId} playlist={playlist} setLastPlaylist={setLastPlaylist} />
        ))}
      </div>
    </>
  );
};

export const Bookmarks = () => {
  const [playlists, setPlaylists] = useState([]);
  const { keyword, setKeyword, search } = useSearch();
  const { page, setLast: setLastPlaylist } = usePageObserver();

  useEffect(() => {
    axios.get(`/bookmarks?page=${page}`).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          setPlaylists(playlists.concat(response.playlists));
          break;
        default:
          alertError(response);
          break;
      }
    });
  }, [page]);

  return (
    <>
      <div className="header__container">
        <PlaylistsHeader keyword={keyword} onChangeKeyword={setKeyword} onPressEnter={search} />
      </div>
      <div className="lists__container">
        {playlists.map((playlist: PlaylistProps) => (
          <Playlist key={playlist.playlistId} playlist={playlist} setLastPlaylist={setLastPlaylist} />
        ))}
      </div>
    </>
  );
};

export const SearchPlaylists = () => {
  const [playlists, setPlaylists] = useState([]);
  const { keyword, setKeyword, search } = useSearch();

  useEffect(() => {
    axios.get(`/playlist/search`, { params: { keyword: keyword } }).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          setPlaylists(playlists.concat(response.playlists));
          break;
        default:
          alertError(response);
          break;
      }
    });
  }, []);

  return (
    <>
      <div className="header__container">
        <PlaylistsHeader keyword={keyword} onChangeKeyword={setKeyword} onPressEnter={search} />
      </div>
      <div className="lists__container">
        {playlists.map((playlist: PlaylistProps) => (
          <Playlist key={playlist.playlistId} playlist={playlist} />
        ))}
      </div>
    </>
  );
};
