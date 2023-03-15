import {PlaylistProps} from "./Playlist";
import {PlaylistEditBox} from "./PlaylistEditBox";
import {StepType} from "../../pages/songs/songs";
import {Icon} from "@iconify/react";
import React from "react";

interface PlaylistInfosProps {
  playlist: PlaylistProps
  setStep?: (stepType: StepType) => void
  bookmarkCount?: number
}
export const PlaylistTitle = ({ playlist, setStep }: PlaylistInfosProps ) => {
  return (
    <div className="title__container">
      <PlaylistEditBox playlist={playlist} setStep={setStep} />
      <p className="page__p--title">{playlist.title}</p>
      <p className="page__p--author">{playlist.author}님의 플레이리스트</p>
    </div>
  );
}

export const PlaylistDetails = ({ playlist, bookmarkCount }: PlaylistInfosProps) => {
  return (
    <>
      <div className="description__container">
        <p className="page__p--description">{playlist.description}</p>
      </div>
      <div className="details__container">
        {/*<a href="#" className="page__p--update_button"><p style={{ padding: '5px' }}>수정하기</p></a>*/}
        <div className="page__p--bookmark__container">
          <Icon style={{ float: 'left', padding: '5px'}} icon="bi:star-fill"/>
          <p style={{ padding: '5px', float: 'left' }}>즐겨찾기 수</p>
          <p className="page__p--bookmark__count">{bookmarkCount && bookmarkCount}</p>
        </div>
      </div>
    </>
  )
}