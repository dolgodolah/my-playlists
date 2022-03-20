import { Link } from "react-router-dom";
import { SongProps } from "../shared/Props";

const Song = ({ song, pid }: SongProps) => {
  return (
    <Link
      to={`/playlist`}
      state={{
        path: "/song/detail",
        pid: `${pid}`,
        sid: `${song.videoid}`,
      }}
      className="song__link"
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
