import { Icon } from "@iconify/react";
import { Dispatch, SetStateAction } from "react";
import moment from "moment";

export interface PlaylistProps {
  author: string;
  playlistId: number;
  title: string;
  updatedDate: string;
  songCount: number;
  setLastPlaylist?: Dispatch<SetStateAction<HTMLAnchorElement | null | undefined>>;
}

const Playlist = (
  {
    author,
    title,
    updatedDate,
    songCount,
    setLastPlaylist,
  }: PlaylistProps
) => {
  return (
    <a href="#" className="playlist__link">
      <div className="playlist__container">
        <div className="playlist__container--left">
          <span className="playlist__span--title">{title}</span>
          <div>
            <span className="playlist__span--updatedDate">{updatedDate}</span>
            <span className="playlist__span--author">{author}</span>
          </div>
        </div>
        <div className="playlist__container--right">
          <span className="playlist__span--amount">{songCount}</span>
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
    </a>
  );
};

export default Playlist;
