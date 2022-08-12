import { useLocation } from "react-router-dom";
import PlayBox from "../components/PlayBox";
import { PlaylistDescription, PlaylistTitle } from "../components/PlaylistDetail";
import Songs from "../components/Songs";
import YoutubeVideo from "../components/YoutubeVideo";
import SearchSongs from "../components/SearchSongs";
import { PlaylistProps, SongProps } from "../shared/Props";

interface StateProps {
  page: string;
  playlist: PlaylistProps;
  playedSong?: SongProps;
  nextSongs?: Array<SongProps>;
}

const Playlist = () => {
  const { page, playlist, playedSong, nextSongs } = useLocation().state as StateProps;
  const Render = () => {
    switch (page) {
      // 선택 플레이리스트 상세화면
      case "showSongs": {
        return (
          <PlayBox
            left={<Songs playlist={playlist} />}
            right={
              <>
                <PlaylistTitle playlist={playlist} />
                <PlaylistDescription playlist={playlist} />
              </>
            }
          />
        );
      }

      // 선택 플레이리스트 노래 재생화면
      case "playSong": {
        return (
          <PlayBox
            left={<Songs playlist={playlist} playedSong={playedSong}/>}
            right={
              <>
                <PlaylistTitle playlist={playlist} />
                <YoutubeVideo playlist={playlist} playedSong={playedSong!} nextSongs={nextSongs!} />
              </>
            }
          />
        );
      }

      // 선택 플레이리스트 노래 제목 검색화면
      case "searchSong": {
        return (
          <PlayBox
            left={<Songs playlist={playlist} />}
            right={
              <>
                <PlaylistTitle playlist={playlist} />
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
