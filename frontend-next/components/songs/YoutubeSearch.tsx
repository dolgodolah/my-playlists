import {SongProps} from "../../../frontend/src/shared/Props";
import useClient from "../hooks/useClient";
import React, {useState} from "react";
import StatusCode from "../../shared/StatusCode";
import alertError from "../../shared/Error";
import {PlaylistProps} from "../playlists/Playlist";

interface YoutubeSearchProps {
  playlist: PlaylistProps
}
export const YoutubeSearch = ({ playlist }: YoutubeSearchProps) => {
  const client = useClient()
  const [keyword, setKeyword] = useState("");
  const [songs, setSongs] = useState([])

  const search = async (e: React.FormEvent) => {
    e.preventDefault()
    const res = await client.get("/youtube-search", {
      q: keyword
    })

    switch (res.statusCode) {
      case StatusCode.OK:
        setSongs(res.songs)
        break
      default:
        alertError(res)
        break
    }
  }

  const addSong = async (song: SongProps) => {
    const res = await client.post("/songs", {
      playlistId: playlist.playlistId,
      title: song.title,
      videoId: song.videoId
    })

    switch (res.statusCode) {
      case StatusCode.OK:
        location.href = `/songs?p=${playlist.playlistId}`
        break
      default:
        alertError(res)
        break
    }
  }

  return (
    <>
      <div className="search-song__container">
        <form onSubmit={search}>
          <input
            placeholder="노래 제목, 가수 이름을 검색하세요. 유튜브 상위 목록 5개를 노출합니다."
            name="keyword"
            className="search-song__input"
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
          />
        </form>
      </div>
      <div className="search-songs-results__container">
        {songs && songs.map((song: SongProps) => (
          <div key={song.videoId} className="search-song-result__container">
            <div>
              <img
                className="search-song-result__image"
                src={`https://i.ytimg.com/vi/${song.videoId}/maxresdefault.jpg`}
                alt="youtube_thumbnail"
              />
            </div>
            <div>
              <span className="search-song-result__span">{song.title}</span>
            </div>
            <div>
              <button
                onClick={() => addSong(song)}
                className="search-song-result__button"
              >
                추가
              </button>
            </div>
          </div>
        ))}
      </div>
    </>
  );
}