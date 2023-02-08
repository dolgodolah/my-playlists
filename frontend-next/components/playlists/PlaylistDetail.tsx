import {PlaylistProps} from "./Playlist";
import {PlaylistEditBox} from "./PlaylistEditBox";
import {StepType} from "../../pages/songs/songs";

interface PlaylistDetailProps {
  playlist: PlaylistProps
  setStep?: (stepType: StepType) => void
}
export const PlaylistTitle = ({ playlist, setStep }: PlaylistDetailProps ) => {
  return (
    <div className="title__container">
      <PlaylistEditBox playlist={playlist} setStep={setStep} />
      <p className="page__p--title">{playlist.title}</p>
      <p className="page__p--author">{playlist.author}님의 플레이리스트</p>
    </div>
  );
}

export const PlaylistDescription = ({ playlist }: PlaylistDetailProps) => {
  return (
    <div className="description__container">
      <p className="page__p--description">{playlist.description}</p>
    </div>
  )
}