import { useLocation } from "react-router-dom";
import PlayBox from "../components/PlayBox";
import Song from "../components/Song";
import { songs } from "../test/user";

const Playlist = () => {
  const page = useLocation().state as string;
  const Render = () => {
    switch (page) {
      // 선택 플레이리스트 상세화면
      case "showSongs": {
        return (
          <PlayBox
            page={page}
            top={<>{/*  플리 title, description */}</>}
            left={
              <>
                {songs?.map((song, index) => (
                  <Song key={index} song={song} />
                ))}
              </>
            }
            right={null}
          />
        );
      }

      // 선택 플레이리스트 노래 재생화면
      case "playSongs": {
        return (
          <PlayBox
            page={page}
            top={<>{/*  플리 title, description */}</>}
            left={
              <>
                {songs?.map((song, index) => (
                  <Song key={index} song={song} />
                ))}
              </>
            }
            right={<>{/* 유튜브 영상 위치 */}</>}
          />
        );
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
  };
  return Render();
};

export default Playlist;
