import { Link } from "react-router-dom";
import { SongProps } from "../shared/Props";

const Song = ({ song }: SongProps) => {
  return (
    <Link
      to="/playlist"
      state={{
        page: "playSongs",
        songId: song.id,
      }}
    >
      <div className="song__container">
        <span className="song__span--title">{song.title}</span>
        <div>
          <span className="song__span--createdDate">{song.createdDate}</span>
        </div>
      </div>
    </Link>
  );
};

export default Song;
