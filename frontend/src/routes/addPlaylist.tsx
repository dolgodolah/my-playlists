import PlayBox from "../components/PlayBox";
import PlaylistAddForm from "../components/PlaylistAddForm";
import Playlists from "../components/Playlists";
import Header from "../components/Header";
import SearchType from "../shared/SearchType";

const AddPlaylist = () => {
  return (
    <PlayBox
      left={
        <>
          <Header type={SearchType.PLAYLIST} />
          <Playlists page={"myPlaylist"} />
        </>
      }
      right={<PlaylistAddForm />}
    />
  );
};

export default AddPlaylist;
