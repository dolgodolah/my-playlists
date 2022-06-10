import { useCallback, useEffect, useState } from "react";
import axios from "axios";
import { SongProps } from "../shared/Props";
import StatusCode from "../shared/StatusCode";
import { useNavigate } from "react-router-dom";
import alertError from "../shared/Error";

export interface YoutubeVideoProps {
  song: SongProps;
}

const YoutubeVideo = ({ song }: YoutubeVideoProps) => {
  const [description, setDescription] = useState(song.description);

  const navigate = useNavigate();

  const onChange = useCallback((e) => {
    setDescription(e.target.value);
  }, []);
  const onClickEdit = () => {
    console.log(description);
    // 새로운 description 저장 로직 추가
  };
  const onClickDelete = () => {
    const ok = window.confirm("노래를 삭제하시겠습니까?");
    if (ok) {
      axios.delete(`/songs/${song.songId}`).then((res) => {
        const response = res.data;
        switch (response.statusCode) {
          case StatusCode.OK:
            navigate(-1);
            break;
          default:
            alertError(response.body);
            break;
        }
      });
    }
  };

  return (
    <div className="youtube__container">
      <iframe
        title="youtube video player"
        className="youtube__video"
        src={`https://www.youtube.com/embed/${song.videoId}`}
        frameBorder="0"
        allowFullScreen
      />
      <div className="description__container--youtube">
        <textarea
          className="description__textarea--youtube"
          value={description} // TODO: null이면 콘솔 경고 뜨는 듯, 개선 필요
          onChange={onChange}
        />
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
