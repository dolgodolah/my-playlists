import { useLocation } from "react-router-dom";
import { PlaylistBox } from "../components/PlayBox";
import { playlists } from "../test/user";

const Home = () => {
  const page = useLocation().state as string;
  return <PlaylistBox page={page} playlists={playlists}/>;
};

export default Home;
