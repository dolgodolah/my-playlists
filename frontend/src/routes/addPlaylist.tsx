import { AddPlaylistForm } from "../components/InputForm";
import PlayBox from "../components/PlayBox";
import Playlist from "../components/Playlist";

const AddPlaylist = () => {
  return (
    <PlayBox
      left={<Playlist page={"myPlaylist"} />}
      right={<AddPlaylistForm />}
    />
  );
};

export default AddPlaylist;
