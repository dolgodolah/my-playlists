import { Icon } from "@iconify/react";
import { useCallback, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";
import { PlaylistProps, SongProps } from "../shared/Props";
import HeaderLogo from "./HeaderLogo";
import moment from "moment";
import {useAsyncSearch, useSearch} from "./hooks/useSearch";

interface SongsProps {
  playlist: PlaylistProps;
  reload?: boolean;
}

const Songs = ({ playlist, reload }: SongsProps) => {
  const [songs, setSongs] = useState([]);
  const callSearchApi = (keyword: string) => {
    axios
      .get("/songs/search", { params: { playlistId: playlist.playlistId, keyword: keyword } })
      .then((res) => {
        const response = res.data;
        switch (response.statusCode) {
          case StatusCode.OK:
            setSongs(response.songs);
            break;
          default:
            alertError(response.message);
            break;
        }
      });
  }

  const { keyword, setKeyword } = useAsyncSearch(callSearchApi)

  useEffect(() => {
    axios.get("/songs", { params: { playlistId: playlist.playlistId } }).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          setSongs(response.songs);
          break;
        default:
          alertError(response.message);
          break;
      }
    })
  }, [playlist && playlist.playlistId]);

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
  }, [reload]);

  return (
    <>
      <div className="header__container">
        <HeaderLogo />
        <div className="search__container">
          <input
            type="text"
            placeholder="노래 검색"
            className="search__input--header"
            value={keyword}
            onChange={setKeyword}
          />
        </div>
      </div>
      <div className="lists__container">
        {songs?.map((song: SongProps, index) => (
          <Link
            key={song.songId}
            to="/playlist"
            state={{
              page: "playSong",
              playlist: playlist,
              playedSong: song,
              nextSongs: songs.slice(index + 1, songs.length),
            }}
          >
            <div className="song__container">
              <span className="song__span--title">{song.title}</span>
              <div>
                <span className="song__span--createdDate">
                  {moment(song.createdDate).format("YYYY년 MM월 DD일 HH:mm:ss")}
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
