import { useLocation } from "react-router-dom";
import PlayBox from "../components/PlayBox";
import PlaylistDetail from "../components/PlaylistDetail";
import Song from "../components/Song";
import YoutubeVideo from "../components/YoutubeVideo";
import SearchSongs from "../components/SearchSongs";
import {PlaylistProps, SongProps} from "../shared/Props";

interface StateProps {
  page: string;
  playlist: PlaylistProps;
  song?: SongProps;
}

const Playlist = () => {
  const { page, playlist, song } = useLocation().state as StateProps;
  console.log(playlist)
  const Render = () => {
    switch (page) {
      // 선택 플레이리스트 상세화면
      case "showSongs": {
        return (
          <PlayBox
            left={<Song />}
            right={
              <>
                <PlaylistDetail playlist={playlist}/>
              </>
            }
          />
        );
      }

      // 선택 플레이리스트 노래 재생화면
      case "playSongs": {
        return (
          <PlayBox
            left={<Song />}
            right={
              <>
                <PlaylistDetail playlist={playlist} />
                <YoutubeVideo videoId={song?.videoId!} description={song?.description!}/>
              </>
            }
          />
        );
      }

      // 선택 플레이리스트 노래 제목 검색화면
      case "searchSong": {
        return (
          <PlayBox
            left={<Song />}
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
