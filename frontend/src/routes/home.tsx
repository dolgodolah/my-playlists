import { useLocation } from "react-router-dom";
import PlayBox from "../components/PlayBox";
import Playlist from "../components/Playlist";
import PlaylistCategory from "../components/PlaylistCategory";

interface StateProps {
  page: string;
}

const Home = () => {
  const { page } = (useLocation().state as StateProps) || {
    page: "myPlaylist",
  };
  return (
    <PlayBox
      left={<Playlist page={page} />}
      right={<PlaylistCategory page={page} />}
    />
  );
};

export default Home;
