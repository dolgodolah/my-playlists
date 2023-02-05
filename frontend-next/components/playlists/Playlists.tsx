import Playlist, {PlaylistProps} from "./Playlist";
import HeaderLogo from "../HeaderLogo";

interface MyPlaylistsProps {
  playlists: PlaylistProps[]
}

export const Playlists = ({ playlists }: MyPlaylistsProps) => {
  return (
    <>
      <div className="header__container">
        <HeaderLogo />
        <div className="search__container">
          <input
            type="text"
            placeholder="플레이리스트 검색"
            className="search__input--header"
            value={""}
            onChange={() => console.log("onChange")}
            onKeyPress={() => console.log("onKeyPress")}
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
          />
        ))}
      </div>
    </>
  );
}