import { useLocation } from "react-router-dom";
import PlayBox from "../components/PlayBox";
import PlaylistDetail from "../components/PlaylistDetail";
import Songs from "../components/Songs";
import YoutubeVideo from "../components/YoutubeVideo";
import SearchSongs from "../components/SearchSongs";
import {PlaylistProps, SongProps} from "../shared/Props";

interface StateProps {
  page: string;
  playlist: PlaylistProps;
  playedSong?: SongProps;
}

const Playlist = () => {
  const { page, playlist, playedSong } = useLocation().state as StateProps;
  const Render = () => {
    switch (page) {
      // 선택 플레이리스트 상세화면
      case "showSongs": {
        return (
          <PlayBox
            left={<Songs playlist={playlist}/>}
            right={
              <>
                <PlaylistDetail playlist={playlist}/>
              </>
            }
          />
        );
      }

      // 선택 플레이리스트 노래 재생화면
      case "playSong": {
        return (
          <PlayBox
            left={<Songs playlist={playlist}/>}
            right={
              <>
                <PlaylistDetail playlist={playlist} />
                <YoutubeVideo song={playedSong!}/>
              </>
            }
          />
        );
      }

      // 선택 플레이리스트 노래 제목 검색화면
      case "searchSong": {
        return (
          <PlayBox
            left={<Songs playlist={playlist}/>}
            right={
              <>
                <PlaylistDetail playlist={playlist} />
                <SearchSongs playlistId={playlist.playlistId} />
              </>
            }
          />
        );
      }
    }
    return null;
  };
  return Render();
};

export default Playlist;
