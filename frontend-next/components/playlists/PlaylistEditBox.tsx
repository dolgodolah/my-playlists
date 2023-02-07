import {PlaylistProps} from "./Playlist";
import React, {useState} from "react";
import {Icon} from "@iconify/react";
import classNames from "classnames";

interface PlaylistEditBoxProps {
  playlist: PlaylistProps
}

export const PlaylistEditBox = ({ playlist }: PlaylistEditBoxProps) => {
  const [isBookmark, setBookmark] = useState(playlist.isBookmark);

  const toggleBookmark = () => {
    console.log("즐겨찾기 toggle")
  }

  const deletePlaylist = () => {
    console.log("플레이리스트 삭제")
  }

  const goToSongAddForm = (e: React.MouseEvent<HTMLAnchorElement>) => {
    e.preventDefault()
    location.href = "/songs/add"
  }

  return (
    <div className="edit-box__container">
      <Icon
        icon="bi:star-fill"
        className={classNames({ isBookmark })}
        onClick={toggleBookmark}
      />
      {playlist.isEditable ?
        <>
          <a href="#" onClick={goToSongAddForm}><Icon icon="carbon:music-add" /></a>
          <Icon icon="carbon:delete" onClick={deletePlaylist} />
        </>
        : null
      }

    </div>
  );
}