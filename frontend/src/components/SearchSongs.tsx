import React, { useCallback, useState } from "react";
import { songs as newSongs } from "../test/user";

interface SongProps {
  id: number;
  videoId: string;
  title: string;
}

const SearchSongs = () => {
  const [keyword, setKeyword] = useState("");
  const [songs, setSongs] = useState([]) as Array<any>;

  const onSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("유튜브 노래 검색 api 호출");
    // 유튜브 노래 검색 api 호출
    setSongs(newSongs);
  };
  const onChange = useCallback((e) => {
    setKeyword(e.target.value);
  }, []);
  const addSong = (song: SongProps) => {
    console.log(song);
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
          <div key={song.id} className="search-song-result__container">
            <div>
              <img
                className="song-search-result__image"
                src={`http://i.ytimg.com/vi/${song.videoId}/maxresdefault.jpg`}
                alt="youtube_thumbnail"
              />
            </div>
            <div>
              <span className="song-search-result__span">{song.title}</span>
            </div>
            <div>
              <button
                onClick={() => addSong(song)}
                className="song-search-result__button"
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
