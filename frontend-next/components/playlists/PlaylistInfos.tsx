import {PlaylistProps} from "./Playlist";
import {PlaylistEditBox} from "./PlaylistEditBox";
import {StepType} from "../../pages/songs/songs";
import {Icon} from "@iconify/react";
import {KeyboardEvent, FormEvent, useEffect, useRef, useState} from "react";

interface PlaylistTitleProps {
  playlist: PlaylistProps
  setStep: (stepType: StepType) => void
  setBookmarkCount: (bookmarkCount: number) => void
  isEditMode?: boolean
}
export const PlaylistTitle = ({ playlist, setStep, setBookmarkCount, isEditMode = false }: PlaylistTitleProps ) => {
  return (
    <div className="title__container">
      <PlaylistEditBox
        playlist={playlist}
        setStep={setStep}
        setBookmarkCount={setBookmarkCount} />
      <p className="page__p--title">{playlist.title}</p>
      <p className="page__p--author">{playlist.author}님의 플레이리스트</p>
    </div>
  );
}

interface PlaylistDetailsProps {
  playlist: PlaylistProps
  bookmarkCount: number
  isEditMode?: boolean
  onClickEdit: (title: string, description: string) => void
}

export const PlaylistDetails = ({ playlist, bookmarkCount, isEditMode = false, onClickEdit }: PlaylistDetailsProps) => {
  const inputRef = useRef<HTMLInputElement>(null)
  const [newDescription, setNewDescription] = useState(playlist.description)

  const onChangeDescription = (e: FormEvent<HTMLInputElement>) => {
    setNewDescription(e.currentTarget.value)
  }

  const handleKeyPress = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      onClickEdit(playlist.title, newDescription)
    }
  }

  useEffect(() => {
    if (isEditMode) {
      inputRef && inputRef.current?.focus()
    }
  }, [isEditMode])

  return (
    <>
      <div className="description__container">
        {isEditMode &&
          <input
            ref={inputRef}
            style={{ resize: 'none' }}
            className="page__p--description"
            value={newDescription}
            onKeyPress={handleKeyPress}
            onChange={onChangeDescription}>
          </input>
        }
        {!isEditMode && <p className="page__p--description">{newDescription}</p>}
      </div>
      <div className="details__container">
        <a href="#" onClick={() => onClickEdit(playlist.title, newDescription)} className="page__p--update_button"><p style={{ padding: '5px' }}>수정하기</p></a>
        <div className="page__p--bookmark__container">
          <Icon style={{ float: 'left', padding: '5px'}} icon="bi:star-fill"/>
          <p style={{ padding: '5px', float: 'left' }}>즐겨찾기 수</p>
          <p className="page__p--bookmark__count">{bookmarkCount}</p>
        </div>
      </div>
    </>
  )
}