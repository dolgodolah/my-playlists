import { VideoProps } from "../shared/Props";

const YoutubeVideo = ({ description, videoId }: VideoProps) => {
  return (
    <div className="youtube__container">
      <video
        tabIndex={-1}
        className="youtube__video"
        controlsList="nodownload"
        src={videoId}
      ></video>
      <div className="description__container--youtube">
        <textarea
          value={description}
          className="description__textarea"
          readOnly
        />
      </div>
    </div>
  );
};

export default YoutubeVideo;
