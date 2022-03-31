import { VideoProps } from "../shared/Props";

const YoutubeVideo = ({ description, youtube }: VideoProps) => {
  return (
    <div className="youtube__container">
      <video
        tabIndex={-1}
        className="youtube__video"
        controlsList="nodownload"
        src={youtube.videoId}
      ></video>
      <div className="description__container--youtube">
        <textarea
          value={description}
          className="description__textarea"
          readOnly
        />
      </div>
      <div className="thumbnail__container--youtube">
        <img
          className="thumbnail__img--youtube"
          src={youtube.thumbnail}
          alt="youtube_thumbnail"
        />
      </div>
    </div>
  );
};

export default YoutubeVideo;
