import Playlist, {PlaylistProps} from "./Playlist";
import HeaderLogo from "../HeaderLogo";
import usePageObserver from "../hooks/usePageObserver";
import useClient from "../hooks/useClient";
import StatusCode from "../../shared/StatusCode";
import alertError from "../../shared/Error";
import {useSearch} from "../hooks/useSearch";

interface PlaylistsProps {
  playlists: PlaylistProps[]
  setPlaylists: (playlists: PlaylistProps[]) => void
}

export const MyPlaylists = ({ playlists, setPlaylists }: PlaylistsProps) => {
  const client = useClient()
  const { keyword, setKeyword, onPressEnter } = useSearch({
    callback: () => searchPlaylists()
  })

  const searchPlaylists = async () => {
    const res = await client.get("/playlists/search", {
      q: keyword
    })
    switch (res.statusCode) {
      case StatusCode.OK:
        setPlaylists(res.playlists)
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
        {playlists && playlists.map((playlist: PlaylistProps) => (
          <Playlist
            key={playlist.playlistId}
            author={playlist.author}
            playlistId={playlist.playlistId}
            title={playlist.title}
            updatedDate={playlist.updatedDate}
            songCount={playlist.songCount}
            description={playlist.description}
            isBookmark={playlist.isBookmark}
            isEditable={playlist.isEditable}
          />
        ))}
      </div>
    </>
  );
}

export const AllPlaylists = ({ playlists, setPlaylists }: PlaylistsProps) => {
  const client = useClient()
  const { keyword, setKeyword, onPressEnter } = useSearch({
    callback: () => searchPlaylists()
  })
  const { setLast: setLastPlaylist } = usePageObserver({
    callback: (page) => getMorePlaylists(page)
  })

  const searchPlaylists = async () => {
    const res = await client.get("/playlists/search-all", {
      q: keyword
    })
    switch (res.statusCode) {
      case StatusCode.OK:
        setPlaylists(res.playlists)
        break
      default:
        alertError(res)
        break
    }
  }

  const getMorePlaylists = async (page: number) => {
    const res = await client.get(`/all-playlists?page=${page}`)
    switch (res.statusCode) {
      case StatusCode.OK:
        setPlaylists(playlists.concat(res.playlists))
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
        {playlists && playlists.map((playlist: PlaylistProps) => (
          <Playlist
            key={playlist.playlistId}
            author={playlist.author}
            playlistId={playlist.playlistId}
            title={playlist.title}
            updatedDate={playlist.updatedDate}
            songCount={playlist.songCount}
            description={playlist.description}
            isBookmark={playlist.isBookmark}
            isEditable={playlist.isEditable}
            // 모든 플레이리스트 조회의 경우 무한 스크롤 페이징 처리를 위해 `observer`를 넘겨준다.
            setLastPlaylist={setLastPlaylist}
          />
        ))}
      </div>
    </>
  );
}