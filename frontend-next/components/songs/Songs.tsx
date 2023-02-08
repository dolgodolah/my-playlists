import {useAsyncSearch} from "../hooks/useSearch";
import useClient from "../hooks/useClient";
import {PlaylistProps} from "../playlists/Playlist";
import StatusCode from "../../shared/StatusCode";
import HeaderLogo from "../HeaderLogo";
import {useState} from "react";
import alertError from "../../shared/Error";
import {Song, SongProps} from "./Song";
import {StepType} from "../../pages/songs/songs";



interface SongsProps {
  playlist: PlaylistProps
  songs: SongProps[]
  setStep?: (stepType: StepType) => void
}
export const Songs = ({ playlist, songs, ...props }: SongsProps) => {
  const client = useClient()
  const [currentSongs, setCurrentSongs] = useState(songs)

  const searchSongs = async (keyword: string) => {
    const res = await client.get("/songs/search", {
      playlistId: playlist.playlistId,
      q: keyword
    })

    switch (res.statusCode) {
      case StatusCode.OK:
        setCurrentSongs(res.songs)
        break
      default:
        alertError(res)
        break
    }
  }
  const { keyword, setKeyword } = useAsyncSearch(searchSongs)

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
          {currentSongs?.map((song: SongProps) => (
            <Song
              key={song.songId}
              songId={song.songId}
              title={song.title}
              videoId={song.videoId}
              createdDate={song.createdDate}
              updatedDate={song.updatedDate}
              isEditable={playlist.isEditable}
              setStep={props.setStep}
            />
          ))}
      </div>
    </>
  );
}