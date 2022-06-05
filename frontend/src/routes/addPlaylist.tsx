import { PlaylistAddForm } from "../components/PlaylistAddForm";
import PlayBox from "../components/PlayBox";
import Playlists from "../components/Playlists";

const AddPlaylist = () => {
  return (
    <PlayBox
      left={<Playlists page={"myPlaylist"} />}
      right={<PlaylistAddForm />}
    />
  );
};

export default AddPlaylist;
