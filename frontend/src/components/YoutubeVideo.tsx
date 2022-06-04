import { useCallback, useState } from "react";


export interface YoutubeVideoProps {
  videoId: string;
  description: string;
}

const YoutubeVideo = ({ videoId, description: preDescription }: YoutubeVideoProps) => {
  const [description, setDescription] = useState(preDescription);
  const onChange = useCallback((e) => {
    setDescription(e.target.value);
  }, []);
  const onClickEdit = () => {
    console.log(description);
    // 새로운 description 저장 로직 추가
  };
  const onClickDelete = () => {
    // 노래 삭제 로직 추가
  };
  return (
    <div className="youtube__container">
      <iframe
        title="youtube video player"
        className="youtube__video"
        src={`https://www.youtube.com/embed/${videoId}`}
        frameBorder="0"
        allowFullScreen
      />
      <div className="description__container--youtube">
        <textarea
          className="description__textarea--youtube"
          value={description}
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
          src={`https://i.ytimg.com/vi/${videoId}/mqdefault.jpg`}
          alt="youtube_thumbnail"
        />
      </div>
    </div>
  );
};

export default YoutubeVideo;
