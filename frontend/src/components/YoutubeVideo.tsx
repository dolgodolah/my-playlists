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
        <textarea className="description__textarea" readOnly>
          {description}
        </textarea>
      </div>
    </div>
  );
};

export default YoutubeVideo;
