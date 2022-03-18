import { useLocation } from "react-router-dom";
import EditBox from "../components/EditBox";
import PlayBox from "../components/PlayBox";
import Playlist from "../components/Playlist";
import PlaylistCategory from "../components/PlaylistCategory";
import { playlists } from "../test/user";

const Home = () => {
  const { pathname } = useLocation();
  return (
    <PlayBox>
      <div className="play-box__container--top">
        <span className="page-title__span">
          <PlaylistCategory pathname={pathname} />
        </span>
        <EditBox pathname={pathname} />
      </div>
      <div className="play-box__container--left">
        <div className="my-playlists__container">
          { playlists.map((playlist) => (
            <Playlist key={playlist.playlistId} playlist={playlist} />
          )) }
        </div>
      </div>
      <div className="play-box__container--right"></div>
    </PlayBox>
  );
};

export default Home;
