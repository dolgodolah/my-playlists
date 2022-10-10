import {useLocation} from "react-router-dom";
import ContainerBox from "../components/ContainerBox";
import Songs from "../components/song/Songs";
import {PlaylistDescription, PlaylistTitle} from "../components/playlist/PlaylistDetail";
import YoutubeVideo from "../components/song/YoutubeVideo";
import SearchSongs from "../components/song/SearchSongs";
import {PlaylistProps, SongProps} from "../shared/Props";

interface StateProps {
  page: string;
  playlist: PlaylistProps;
  playedSong?: SongProps;
  nextSongs?: Array<SongProps>;
}

export const SongList = () => {
  const { page, playlist, playedSong, nextSongs } = useLocation().state as StateProps;
  const Render = () => {
    switch (page) {
      // 선택 플레이리스트 상세화면
      case "showSongs": {
        return (
          <ContainerBox
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
          <ContainerBox
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
          <ContainerBox
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