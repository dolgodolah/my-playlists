import {PlaylistProps} from "./Playlist";
import React, {useState} from "react";
import {Icon} from "@iconify/react";
import classNames from "classnames";
import {StepType} from "../../pages/songs/songs";

interface PlaylistEditBoxProps {
  playlist: PlaylistProps
  setStep?: (stepType: StepType) => void
}

export const PlaylistEditBox = ({ playlist, setStep }: PlaylistEditBoxProps) => {
  const [isBookmark, setBookmark] = useState(playlist.isBookmark);

  const toggleBookmark = () => {
    console.log("즐겨찾기 toggle")
  }

  const deletePlaylist = () => {
    console.log("플레이리스트 삭제")
  }

  const goToSongAddStep = (e: React.MouseEvent<HTMLAnchorElement>) => {
    e.preventDefault()
    setStep && setStep(StepType.ADD)
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
          <a href="#" onClick={goToSongAddStep}><Icon icon="carbon:music-add" /></a>
          <Icon icon="carbon:delete" onClick={deletePlaylist} />
        </>
        : null
      }

    </div>
  );
}