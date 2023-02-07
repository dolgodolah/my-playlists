import {PlaylistProps} from "./Playlist";
import {PlaylistEditBox} from "./PlaylistEditBox";

interface PlaylistDetailProps {
  playlist: PlaylistProps
}
export const PlaylistDetail = ({ playlist }: PlaylistDetailProps ) => {
  return (
    <>
      <div className="title__container">
        <PlaylistEditBox playlist={playlist} />
        <p className="page__p--title">{playlist.title}</p>
        <p className="page__p--author">{playlist.author}님의 플레이리스트</p>
      </div>
      <div className="description__container">
        <p className="page__p--description">{playlist.description}</p>
      </div>
    </>
  );
}