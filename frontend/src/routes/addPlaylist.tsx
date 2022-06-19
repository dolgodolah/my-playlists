import PlayBox from "../components/PlayBox";
import PlaylistAddForm from "../components/PlaylistAddForm";
import {MyPlaylists} from "../components/Playlists";

const AddPlaylist = () => {
  return (
    <PlayBox
      left={<MyPlaylists />}
      right={<PlaylistAddForm />}
    />
  );
};

export default AddPlaylist;
