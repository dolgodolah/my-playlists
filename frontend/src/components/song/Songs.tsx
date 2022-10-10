import { Icon } from "@iconify/react";
import { useCallback, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import StatusCode from "../../shared/StatusCode";
import alertError from "../../shared/Error";
import { PlaylistProps, SongProps } from "../../shared/Props";
import HeaderLogo from "../HeaderLogo";
import moment from "moment";
import { useAsyncSearch, useSearch } from "../hooks/useSearch";

interface SongsProps {
  playlist: PlaylistProps;
  playedSong?: SongProps;
}

const Songs = ({ playlist, playedSong }: SongsProps) => {
  const [songs, setSongs] = useState([]);
  const navigate = useNavigate();

  const callGetSongsApi = () => {
    axios.get("/songs", { params: { playlistId: playlist.playlistId } }).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          setSongs(response.songs);
          break;
        default:
          alertError(response);
          break;
      }
    });
  }

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
            alertError(response);
            break;
        }
      });
  };

  const callDeleteSongApi = (song: SongProps) => {
    axios.delete(`/songs/${song.songId}`).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          // 재생되고 있는 노래가 삭제된 경우 해당 플레이리스트 초기 화면으로 간다.
          if (playedSong?.songId === song.songId) {
            navigate("/playlist", {
              state: {
                page: "showSongs",
                playlist: playlist,
              },
            });
          } else {
            // 다른 노래가 삭제됐다면 노래 목록을 갱신시킨다.
            callGetSongsApi()
          }
          break;
        default:
          alertError(response);
          break;
      }
    });
  }

  const { keyword, setKeyword } = useAsyncSearch(callSearchApi);
  const deleteSong = (song: SongProps) => {
    const ok = window.confirm("노래를 삭제하시겠습니까?");
    if (ok) {
      callDeleteSongApi(song);
    }
  };

  useEffect(() => callGetSongsApi(), [playlist]);

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
          <div className="song-link__container" key={song.songId}>
            <Link
              to="/songs"
              state={{
                page: "playSong",
                playlist: playlist,
                playedSong: song,
                nextSongs: songs.slice(index + 1, songs.length),
              }}
            >
              <div className="song__container">
                <div className="song__container--forward">
                  <span className="song__span--title">{song.title}</span>
                  <div>
                    <span className="song__span--createdDate">
                      {moment(song.createdDate).format("YYYY년 MM월 DD일 HH:mm:ss")}
                    </span>
                  </div>
                </div>
                <div className="hidden-background__container">
                  <span className="hidden-background__container__span">
                    <Icon icon="codicon:debug-start" />
                  </span>
                </div>
              </div>
            </Link>
            <Icon className="song-delete__icon" icon="carbon:delete" onClick={() => deleteSong(song)} />
          </div>
        ))}
      </div>
    </>
  );
};

export default Songs;
