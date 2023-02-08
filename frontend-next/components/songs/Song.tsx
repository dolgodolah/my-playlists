import {Icon} from "@iconify/react";
import React from "react";
import {StepType} from "../../pages/songs/songs";

export interface SongProps {
  songId: number
  title: string
  videoId: string
  description: string
  createdDate: string
  updatedDate: string
  isEditable: boolean
  setPlayedSong?: (song: SongProps) => void
  setStep?: (stepType: StepType) => void
}

export const Song = ({ songId, title, videoId, description, createdDate, updatedDate, isEditable, ...props }: SongProps) => {
  const deleteSong = () => {
    console.log("노래 삭제")
  }

  const playSong = (e: React.MouseEvent<HTMLAnchorElement>) => {
    e.preventDefault()
    props.setPlayedSong && props.setPlayedSong({
      songId, title, videoId, description, createdDate, updatedDate, isEditable
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