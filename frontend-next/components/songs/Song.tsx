import { Icon } from "@iconify/react";

export interface SongProps {
  songId: number
  title: string
  videoId: String
  description?: string
  createdDate: string
  updatedDate: string
  isEditable: boolean
}

export const Song = ({ title, updatedDate, isEditable }: SongProps) => {
  const deleteSong = () => {
    console.log("노래 삭제")
  }

  return (
    <>
      <a href="#">
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
    </>
  )
}