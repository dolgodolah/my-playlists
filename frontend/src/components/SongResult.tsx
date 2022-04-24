import { SongProps } from "../shared/Props";

const SongResult = ({ song }: SongProps) => {
  const addSong = () => {
    console.log(song.videoId);
    // TODO: 노래 추가 로직
  };
  return (
    <div className="song-result__container">
      <div>
        <img
          className="song-result__image"
          src={`http://i.ytimg.com/vi/${song.videoId}/maxresdefault.jpg`}
          alt="youtube_thumbnail"
        />
      </div>
      <div>
        <span className="song-result__span">{song.title}</span>
      </div>
      <div>
        <button onClick={addSong} className="song-result__button">
          추가
        </button>
      </div>
    </div>
  );
};

export default SongResult;
