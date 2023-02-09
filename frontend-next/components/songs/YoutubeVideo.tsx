import ReactPlayer from "react-player";
import {SongProps} from "./Song";
import {useEffect, useState} from "react";
import {PlaylistProps} from "../playlists/Playlist";
import useClient from "../hooks/useClient";
import StatusCode from "../../shared/StatusCode";
import alertError from "../../shared/Error";

interface YoutubeVideoProps {
  playlist: PlaylistProps
  songs: SongProps[]
  playedSong: SongProps
  refreshSongs: () => void
  setPlayedSong?: (song: SongProps) => void
}
export const YoutubeVideo = ({ playedSong, songs, playlist, refreshSongs, ...props }: YoutubeVideoProps) => {
  const client = useClient()
  const [description, setDescription] = useState(playedSong.description)

  useEffect(() => {
    setDescription(playedSong.description)
  }, [playedSong.description])

  const onClickEdit = async () => {
    const ok = window.confirm("노래 설명을 수정하시겠습니까?")
    if (ok) {
      const res = await client.put(`/songs?songId=${playedSong.songId}`, {
        title: playedSong.title,
        description
      })

      switch (res.statusCode) {
        case StatusCode.OK:
          refreshSongs()
          break
        default:
          alertError(res)
          break
      }
    }
  }

  const playNextSong = () => {
    const playedSongIndex = songs.findIndex((song) => song.songId === playedSong.songId)

    // 재생되고 있는 노래가 마지막 노래면 페이지 새로고침
    if (songs.length === playedSongIndex + 1) {
      location.reload()
      return
    }

    // 다음 노래가 있으면 화면 리랜더링
    const nextSong = songs[playedSongIndex + 1]
    props.setPlayedSong && props.setPlayedSong(nextSong)
  }

  return (
    <div className="youtube__container">
      <ReactPlayer
        className="youtube__video"
        url={`https://www.youtube.com/watch?v=${playedSong.videoId}`}
        playing={true}
        onEnded={playNextSong}
        controls={true}
      />
      <div className="description__container--youtube">
        <textarea className="description__textarea--youtube" value={description} onChange={(e) => setDescription(e.target.value)} />
      </div>

      <div className="button__container--youtube">
        {playlist.isEditable ?
          <span className="button__span--edit" onClick={onClickEdit}>
            수정
          </span>
          : null
        }
      </div>
      <div className="thumbnail__container--youtube">
        <img
          className="thumbnail__img--youtube"
          src={`https://i.ytimg.com/vi/${playedSong.videoId}/mqdefault.jpg`}
          alt="youtube_thumbnail"
        />
      </div>
    </div>
  );
}