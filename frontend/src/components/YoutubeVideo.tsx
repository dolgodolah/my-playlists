import { useCallback, useEffect, useState } from "react";
import axios from "axios";
import { PlaylistProps, SongProps } from "../shared/Props";
import StatusCode from "../shared/StatusCode";
import { useNavigate } from "react-router-dom";
import alertError from "../shared/Error";
import YouTube from "react-youtube";

export interface YoutubeVideoProps {
  playlist: PlaylistProps;
  song: SongProps;
}

const YoutubeVideo = ({ playlist, song }: YoutubeVideoProps) => {
  const [description, setDescription] = useState(song.description || "");

  const navigate = useNavigate();

  const onChange = useCallback((e) => {
    setDescription(e.target.value);
  }, []);
  const onClickEdit = () => {
    const ok = window.confirm("노래 설명을 수정하시겠습니까?");
    if (ok) {
      axios
        .put(`/songs/${song.songId}`, {
          title: song.title,
          description,
        })
        .then((res) => {
          const response = res.data;
          switch (response.statusCode) {
            case StatusCode.OK:
              break;
            default:
              alertError(response.message);
              break;
          }
        });
    }
  };
  const onClickDelete = () => {
    const ok = window.confirm("노래를 삭제하시겠습니까?");
    if (ok) {
      axios.delete(`/songs/${song.songId}`).then((res) => {
        const response = res.data;
        switch (response.statusCode) {
          case StatusCode.OK:
            navigate("/playlist", {
              state: {
                page: "showSongs",
                playlist: playlist,
              },
            });
            break;
          default:
            alertError(response.message);
            break;
        }
      });
    }
  };

  const opts = {
    width: "720",
    height: "405",
    playerVars: {
      autoplay: 1,
    },
  };

  return (
    <div className="youtube__container">
      <YouTube title="youtube video player" className="youtube__video" videoId={song.videoId} opts={opts} />
      <div className="description__container--youtube">
        <textarea className="description__textarea--youtube" value={description} onChange={onChange} />
      </div>
      <div className="button__container--youtube">
        <span className="button__span--edit" onClick={onClickEdit}>
          수정
        </span>
        <span className="button__span--delete" onClick={onClickDelete}>
          삭제
        </span>
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
};

export default YoutubeVideo;
