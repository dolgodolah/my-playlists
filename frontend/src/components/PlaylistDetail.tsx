import EditBox from "./EditBox";
import {PlaylistProps} from "../shared/Props";

interface PlaylistDetailProps {
  playlist: PlaylistProps
}

const PlaylistDetail = ({ playlist }: PlaylistDetailProps) => {
  return (
    <div className="title__container">
      <EditBox playlist={playlist} />
      <span className="page__span--title">{playlist.title}</span>
      <span className="page__span--sub">{playlist.description}</span>
    </div>
  );
};

export default PlaylistDetail;
