import { Icon } from "@iconify/react";
import moment from "moment";
import { Dispatch, SetStateAction } from "react";
import { Link } from "react-router-dom";
import { PlaylistProps } from "../../shared/Props";

interface NewPlaylistProps {
  playlist: PlaylistProps;
  setLastPlaylist?: Dispatch<SetStateAction<HTMLAnchorElement | null | undefined>>;
}

const Playlist = ({ playlist, setLastPlaylist }: NewPlaylistProps) => {
  return (
    <Link
      to="/songs"
      state={{
        page: "showSongs",
        playlist: playlist,
      }}
      ref={setLastPlaylist}
      className="playlist__link"
    >
      <div className="playlist__container">
        <div className="playlist__container--left">
          <span className="playlist__span--title">{playlist.title}</span>
          <div>
            <span className="playlist__span--updatedDate">
              {moment(playlist.updatedDate).format("YYYY년 MM월 DD일")}
            </span>
            <span className="playlist__span--author">{playlist.author}</span>
          </div>
        </div>
        <div className="playlist__container--right">
          <span className="playlist__span--amount">{playlist.songCount}</span>
          <div className="playlist-icon__container">
            <Icon icon="bxs:playlist" />
          </div>
        </div>
        <div className="hidden-background__container">
          <span className="hidden-background__container__span">
            <Icon icon="codicon:debug-start" />
          </span>
        </div>
      </div>
    </Link>
  );
};

export default Playlist;
