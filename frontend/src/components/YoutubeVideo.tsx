import {SongProps} from "../shared/Props";

const YoutubeVideo = ({ song }: SongProps) => {
  return (
    <div className="youtube__container">
      <video
        tabIndex={-1}
        className="youtube__video"
        controlsList="nodownload"
        src={song.videoId}
      ></video>
      <div className="description__container--youtube">
        <textarea
          value={song.description}
          className="description__textarea"
          readOnly
        />
      </div>
      <div className="thumbnail__container--youtube">
        <img
          className="thumbnail__img--youtube"
          src={song.thumbnail}
          alt="youtube_thumbnail"
        />
      </div>
    </div>
  );
};

export default YoutubeVideo;
