import { useLocation } from "react-router-dom";
import PlayBox from "../components/PlayBox";
import Playlist from "../components/Playlist";
import PlaylistCategory from "../components/PlaylistCategory";
import { playlists } from "../test/user";

const Home = () => {
  const page = (useLocation().state as string) || "myPlaylist";
  return (
    <PlayBox
      page={page}
      top={<PlaylistCategory page={page} />}
      left={
        page === "allPlaylist" ? (
          <> {/*모든 플레이리스트 */}</>
        ) : page === "bookmarks" ? (
          <>{/*즐겨찾기한 플레이리스트 */}</>
        ) : (
          <>
            {playlists?.map((playlist, index) => (
              <Playlist key={index} playlist={playlist} />
            ))}
          </>
        )
      }
      right={null}
    />
  );
};

export default Home;
