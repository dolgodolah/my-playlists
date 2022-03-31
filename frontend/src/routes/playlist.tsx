import { useLocation } from "react-router-dom";
import PlayBox from "../components/PlayBox";
import Song from "../components/Song";
import YoutubeVideo from "../components/YoutubeVideo";
import { StateProps } from "../shared/Props";
import { songs, youtubes } from "../test/user";

const Playlist = () => {
  const { page, playlistId, songId } = useLocation().state as StateProps;
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
        const songResponse = songs.find((song) => song.id === songId);
        const youtubeResponse = youtubes.find(
          (youtube) => youtube.videoId === songResponse.videoId
        );
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
            right={
              <>
                <YoutubeVideo
                  description={songResponse.description}
                  youtube={youtubeResponse}
                />
              </>
            }
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
