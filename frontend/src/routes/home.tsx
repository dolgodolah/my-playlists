import { useLocation } from "react-router-dom";
import PlayBox from "../components/PlayBox";
import Playlist from "../components/Playlist";
import PlaylistCategory from "../components/PlaylistCategory";
import { playlists } from "../test/user";

const Home = () => {
  const page = useLocation().state as string;
  return (
    <PlayBox
      page={page}
      top={
        <span className="page-title__span">
          <PlaylistCategory page={page} />
        </span>
      }
      left={
        page === "allPlaylist" ? (
          {
            /*모든 플레이리스트 */
          }
        ) : page === "bookmarks" ? (
          {
            /*즐겨찾기한 플레이리스트 */
          }
        ) : (
          <div className="my-playlists__container">
            {playlists?.map((playlist, index) => (
              <Playlist key={index} playlist={playlist} />
            ))}
          </div>
        )
      }
      right={null}
    />
  );
};

export default Home;
