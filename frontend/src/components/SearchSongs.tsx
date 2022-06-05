import React, { useCallback, useState } from "react";
import {SongProps} from "../shared/Props";
import axios from "axios";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";

interface SearchSongsProps {
  playlistId: number;
}

const SearchSongs = ({ playlistId }: SearchSongsProps) => {
  const [keyword, setKeyword] = useState("");
  const [songs, setSongs] = useState([]) as Array<any>;

  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    axios.get("/youtube_search", { params: { keyword: keyword } }).then((res) => {
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
  };

  const onChange = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);

  const addSong = (song: SongProps) => {
    console.log(song);
    console.log(playlistId)
    // TODO: 노래 추가 로직
  };

  return (
    <>
      <div className="search-song__container">
        <form onSubmit={onSubmit}>
          <input
            placeholder="노래 검색"
            name="keyword"
            className="search-song__input"
            value={keyword}
            onChange={onChange}
          />
        </form>
      </div>
      <div className="search-songs-results__container">
        {songs.map((song: SongProps) => (
          <div key={song.songId} className="search-song-result__container">
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
};

export default SearchSongs;
