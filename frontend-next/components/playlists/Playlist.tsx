import { Icon } from "@iconify/react";
import React, { Dispatch, SetStateAction } from "react";

export interface PlaylistProps {
  playlistId: string
  title: string
  description: string
  updatedDate: string
  author: string
  isBookmark: boolean
  songCount: number
  isEditable: boolean
  setLastPlaylist?: Dispatch<SetStateAction<HTMLAnchorElement | null | undefined>>;
}

const Playlist = (
  {
    author,
    playlistId,
    title,
    updatedDate,
    songCount,
    setLastPlaylist,
  }: PlaylistProps
) => {
  const goToSongs = (e: React.MouseEvent<HTMLAnchorElement>) => {
    e.preventDefault()
    location.href = `/songs?p=${playlistId}`
  }

  return (
    <a href="#" onClick={goToSongs} className="playlist__link" ref={setLastPlaylist && setLastPlaylist}>
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
