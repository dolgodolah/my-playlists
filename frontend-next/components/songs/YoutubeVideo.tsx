import ReactPlayer from "react-player";
import {SongProps} from "./Song";
import {useCallback, useEffect, useState} from "react";
import {PlaylistProps} from "../playlists/Playlist";
import useClient from "../hooks/useClient";
import StatusCode from "../../shared/StatusCode";
import alertError from "../../shared/Error";

interface YoutubeVideoProps {
  playlist: PlaylistProps
  song: SongProps
  refreshSongs: () => void
}
export const YoutubeVideo = ({ song, playlist, refreshSongs }: YoutubeVideoProps) => {
  const client = useClient()
  const [description, setDescription] = useState(song.description)

  useEffect(() => {
    setDescription(song.description)
  }, [song.songId])

  const onClickEdit = async () => {
    const ok = window.confirm("노래 설명을 수정하시겠습니까?")
    if (ok) {
      const res = await client.put(`/songs?songId=${song.songId}`, {
        title: song.title,
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
    console.log("다음 노래 재생")
  }

  return (
    <div className="youtube__container">
      <ReactPlayer
        className="youtube__video"
        url={`https://www.youtube.com/watch?v=${song.videoId}`}
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
          src={`https://i.ytimg.com/vi/${song.videoId}/mqdefault.jpg`}
          alt="youtube_thumbnail"
        />
      </div>
    </div>
  );
}