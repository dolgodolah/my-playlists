import { useLocation } from "react-router-dom";
import PlayBox from "../components/PlayBox";
import {AllPlaylists, Bookmarks, MyPlaylists} from "../components/Playlists";
import PlaylistCategory from "../components/PlaylistCategory";

interface StateProps {
  page: string;
}

const Home = () => {
  const { page } = (useLocation().state as StateProps) || {
    page: "myPlaylist",
  };

  const render = () => {
    switch (page) {
      case "myPlaylist":
        return (
          <PlayBox
            left={<MyPlaylists />}
            right={<PlaylistCategory page={page} />}
          />
        );
        break;
      case "allPlaylist":
        return (
          <PlayBox
            left={<AllPlaylists />}
            right={<PlaylistCategory page={page} />}
          />
        );
        break;
      case "bookmarks":
        return (
          <PlayBox
            left={<Bookmarks />}
            right={<PlaylistCategory page={page} />}
          />
        );
      default:
        return null;
    }
  }

  return render();
};

export default Home;
