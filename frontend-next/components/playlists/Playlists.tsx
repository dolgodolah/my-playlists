import Playlist, {PlaylistProps} from "./Playlist";
import HeaderLogo from "../HeaderLogo";
import {useState} from "react";
import useClient from "../hooks/useClient";
import StatusCode from "../../shared/StatusCode";
import alertError from "../../shared/Error";
import {useSearch} from "../hooks/useSearch";

interface PlaylistsProps {
  playlists: PlaylistProps[]
}

export const MyPlaylists = ({ playlists }: PlaylistsProps) => {
  const client = useClient()
  const [currentPlaylists, setCurrentPlaylists] = useState(playlists)
  const { keyword, setKeyword, onPressEnter } = useSearch({
    callback: () => searchPlaylists()
  })

  const searchPlaylists = async () => {
    const res = await client.get("/playlists/search", {
      q: keyword
    })
    switch (res.statusCode) {
      case StatusCode.OK:
        setCurrentPlaylists(res.playlists)
        break
      default:
        alertError(res)
        break
    }
  }

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
            onChange={setKeyword}
            onKeyPress={onPressEnter}
          />
        </div>
      </div>
      <div className="lists__container">
        {currentPlaylists && currentPlaylists.map((playlist: PlaylistProps) => (
          <Playlist
            key={playlist.playlistId}
            author={playlist.author}
            playlistId={playlist.playlistId}
            title={playlist.title}
            updatedDate={playlist.updatedDate}
            songCount={playlist.songCount}
          />
        ))}
      </div>
    </>
  );
}

export const AllPlaylists = ({ playlists }: PlaylistsProps) => {
  const client = useClient()
  const [allPlaylists, setAllPlaylists] = useState(playlists)
  const { keyword, setKeyword, onPressEnter } = useSearch({
    callback: () => searchPlaylists()
  })

  const searchPlaylists = async () => {
    const res = await client.get("/playlists/search-all", {
      q: keyword
    })
    switch (res.statusCode) {
      case StatusCode.OK:
        setAllPlaylists(res.playlists)
        break
      default:
        alertError(res)
        break
    }
  }

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
            onChange={setKeyword}
            onKeyPress={onPressEnter}
          />
        </div>
      </div>
      <div className="lists__container">
        {allPlaylists && allPlaylists.map((playlist: PlaylistProps) => (
          <Playlist
            key={playlist.playlistId}
            author={playlist.author}
            playlistId={playlist.playlistId}
            title={playlist.title}
            updatedDate={playlist.updatedDate}
            songCount={playlist.songCount}
          />
        ))}
      </div>
    </>
  );
}