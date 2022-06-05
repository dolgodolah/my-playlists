import { PlaylistAddForm } from "../components/PlaylistAddForm";
import PlayBox from "../components/PlayBox";
import Playlist from "../components/Playlist";

const AddPlaylist = () => {
  return (
    <PlayBox
      left={<Playlist page={"myPlaylist"} />}
      right={<PlaylistAddForm />}
    />
  );
};

export default AddPlaylist;
