import { useLocation } from "react-router-dom";
import EditBox from "../components/EditBox";
import PlayBox from "../components/PlayBox";
import Playlist from "../components/Playlist";
import PlaylistCategory from "../components/PlaylistCategory";
import { userObj, PlaylistResponse } from "../test/user";

const Home = () => {
  const { pathname } = useLocation();
  return (
    <PlayBox>
      <div className="play-box__container--top">
        <span className="page-title__span">
          {userObj.name}의 플레이리스트 목록
        </span>
        <EditBox pathname={pathname} />
      </div>
      <div className="play-box__container--left">
        <PlaylistCategory pathname={pathname} />
      </div>
      <div className="play-box__container--right">
        <div className="my-playlists__container">
          {PlaylistResponse.map((playlist) => (
            <Playlist key={playlist.playlistId} playlist={playlist} />
          ))}
        </div>
      </div>
    </PlayBox>
  );
};

export default Home;
