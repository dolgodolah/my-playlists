import {PlaylistProps} from "./Playlist";
import React, {useState} from "react";
import {Icon} from "@iconify/react";
import classNames from "classnames";
import {StepType} from "../../pages/songs/songs";
import useClient from "../hooks/useClient";
import StatusCode from "../../shared/StatusCode";
import alertError from "../../shared/Error";

interface PlaylistEditBoxProps {
  playlist: PlaylistProps
  setStep: (stepType: StepType) => void
  setBookmarkCount: (bookmarkCount: number) => void
}

export const PlaylistEditBox = ({ playlist, setStep, setBookmarkCount }: PlaylistEditBoxProps) => {
  const client = useClient()
  const [isBookmark, setBookmark] = useState(playlist.isBookmark);

  const toggleBookmark = async () => {
    const res = await client.post(`/bookmarks?p=${playlist.playlistId}`)
    switch (res.statusCode) {
      case StatusCode.OK:
        setBookmark(!isBookmark)
        const _res = await getBookmarkCount()
        setBookmarkCount(_res.bookmarkCount)
        break
      default:
        alertError(res)
    }
  }

  const getBookmarkCount = () => {
    return client.get(`/count-bookmarks?p=${playlist.playlistId}`);
  }

  const deletePlaylist = async () => {
    const ok = window.confirm("플레이리스트를 삭제하시겠습니까?")
    if (ok) {
      const res = await client._delete(`/playlists?p=${playlist.playlistId}`)
      switch (res.statusCode) {
        case StatusCode.OK:
          location.href = "/playlists"
          break
        default:
          alertError(res)
          break
      }
    }
  }

  const goToSongAddStep = (e: React.MouseEvent<HTMLAnchorElement>) => {
    e.preventDefault()
    setStep(StepType.ADD)
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