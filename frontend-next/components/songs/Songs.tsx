import {useAsyncSearch} from "../hooks/useSearch";
import useClient from "../hooks/useClient";
import {PlaylistProps} from "../playlists/Playlist";
import StatusCode from "../../shared/StatusCode";
import HeaderLogo from "../HeaderLogo";
import { Icon } from "@iconify/react";
import {useState} from "react";
import alertError from "../../shared/Error";

interface SongProps {
  songId: number
  title: string
  videoId: String
  description?: string
  createdDate: string
  updatedDate: string
}

interface SongsProps {
  playlist: PlaylistProps
  songs: SongProps[]
}
export const Songs = ({ playlist, songs }: SongsProps) => {
  const client = useClient()
  const [currentSongs, setCurrentSongs] = useState(songs)

  const searchSongs = async () => {
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
  const deleteSong = () => {
    console.log("노래 삭제")
  }

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
          <div className="song-link__container" key={song.songId}>
            <a href="#">
              <div className="song__container">
                <div className="song__container--forward">
                  <span className="song__span--title">{song.title}</span>
                  <div>
                    <span className="song__span--createdDate">
                      {song.updatedDate}
                    </span>
                  </div>
                </div>
                <div className="hidden-background__container">
                  <span className="hidden-background__container__span">
                    <Icon icon="codicon:debug-start" />
                  </span>
                </div>
              </div>
            </a>
            {playlist.isEditable ?
              <Icon className="song-delete__icon" icon="carbon:delete" onClick={deleteSong} />
              : null
            }
          </div>
        ))}
      </div>
    </>
  );
}