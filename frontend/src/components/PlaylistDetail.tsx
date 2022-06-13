import EditBox from "./EditBox";
import { PlaylistProps } from "../shared/Props";

interface PlaylistTitleProps {
  playlist: PlaylistProps;
}

export const PlaylistTitle = ({ playlist }: PlaylistTitleProps) => {
  return (
    <div className="title__container">
      <EditBox playlist={playlist} />
      <p className="page__p--title">{playlist.title}</p>
      <p className="page__p--author">{playlist.author}님의 플레이리스트</p>
    </div>
  );
};

export const PlaylistDescription = ({ playlist }: PlaylistTitleProps) => {
  return (
    <div className="description__container">
      <p className="page__p--description">{playlist.description}</p>
    </div>
  );
};
