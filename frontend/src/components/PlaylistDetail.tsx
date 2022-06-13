import EditBox from "./EditBox";
import { PlaylistProps } from "../shared/Props";

interface PlaylistDetailProps {
  playlist: PlaylistProps;
}

const PlaylistDetail = ({ playlist }: PlaylistDetailProps) => {
  return (
    <div className="title__container">
      <EditBox playlist={playlist} />
      <p className="page__p--title">{playlist.title}</p>
      <p className="page__p--sub">{playlist.description}</p>
    </div>
  );
};

export default PlaylistDetail;
