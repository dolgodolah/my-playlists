import { Icon } from "@iconify/react";
import { Link } from "react-router-dom";

interface PlaylistProps {
  playlist: Record<string, any>;
}

const Playlist = ({ playlist }: PlaylistProps) => {
  return (
    <Link
      to={`/playlist`}
      state={{
        path: "/playlistDetail",
        pid: `${playlist.playlistId}`,
      }}
      className="playlist__link"
    >
      <div className="playlist__container">
        <div className="playlist__container--left">
          <span className="playlist__span--title">{playlist.title}</span>
          <div>
            <span className="playlist__span--updatedDate">
              {playlist.updatedDate}
            </span>
            <span className="playlist__span--author">{playlist.author}</span>
          </div>
        </div>
        <div className="playlist__container--right">
          <span className="playlist__span--amount">
            {playlist.songs.length}
          </span>
          <div className="playlist-icon__container">
            <Icon icon="bxs:playlist" />
          </div>
        </div>
        <div className="hidden-background__container">
          <span className="hidden-background__containe__span">
            <Icon icon="codicon:debug-start" />
          </span>
        </div>
      </div>
    </Link>
  );
};

export default Playlist;
