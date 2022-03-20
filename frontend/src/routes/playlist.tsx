import {useLocation} from "react-router-dom";
import {PlaySong, SongBox} from "../components/PlayBox";
import PlaylistCategory from "../components/PlaylistCategory";
import EditBox from "../components/EditBox";
import Song from "../components/Song"
import {songs} from "../test/user";

const Playlist = () => {
  const page = useLocation().state as string;

  const Render = () => {
    switch (page) {
      // 선택 플레이리스트 상세화면
      case "showSongs": {
        return <SongBox page={page} songs={songs} />;
      }

      // 선택 플레이리스트 노래 재생화면
      case "playSongs": {
        return <PlaySong page={page} songs={songs} />;
      }

      // 선택 플레이리스트 노래 제목 검색화면
      case "searchSong": {
        return null;
      }

      // 선택 플레이리스트 노래 설명 수정화면
      case "updateSong": {
        return null;
      }
    }

    return null;
  }

  return Render();
};

export default Playlist;
