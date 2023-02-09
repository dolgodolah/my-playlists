import {Icon} from "@iconify/react";
import React from "react";
import {StepType} from "../../pages/songs/songs";
import useClient from "../hooks/useClient";
import StatusCode from "../../shared/StatusCode";
import alertError from "../../shared/Error";

export interface SongProps {
  songId: number
  title: string
  videoId: string
  description: string
  createdDate: string
  updatedDate: string
  isEditable: boolean
  isPlay: boolean
  refreshSongs?: () => void
  setPlayedSong?: (song: SongProps) => void
  setStep?: (stepType: StepType) => void
}

export const Song = ({ songId, title, videoId, description, createdDate, updatedDate, isEditable, isPlay, ...props }: SongProps) => {
  const client = useClient()

  const deleteSong = async () => {
    const res = await client._delete(`/songs?songId=${songId}`)
    switch (res.statusCode) {
      case StatusCode.OK:
        // 재생되고 있는 노래를 삭제한 경우 페이지 새로고침
        if (isPlay) {
          location.reload()
        } else {
          // 다른 노래를 삭제한 경우 수록곡 목록만 리랜더링
          props.refreshSongs && props.refreshSongs()
        }
        break
      default:
        alertError(res)
        break
    }
  }

  const playSong = (e: React.MouseEvent<HTMLAnchorElement>) => {
    e.preventDefault()
    props.setPlayedSong && props.setPlayedSong({
      songId, title, videoId, description, createdDate, updatedDate, isEditable, isPlay: true
    })
    props.setStep && props.setStep(StepType.PLAY)
  }

  return (
    <div className="song-link__container">
      <a href="#" onClick={playSong}>
        <div className="song__container">
          <div className="song__container--forward">
            <span className="song__span--title">{title}</span>
            <div>
              <span className="song__span--createdDate">{updatedDate}</span>
            </div>
          </div>
          <div className="hidden-background__container">
            <span className="hidden-background__container__span">
              <Icon icon="codicon:debug-start" />
            </span>
          </div>
        </div>
      </a>
      {isEditable ?
        <Icon className="song-delete__icon" icon="carbon:delete" onClick={deleteSong} />
        : null
      }
    </div>
  )
}