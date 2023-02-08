import withPageContext, {PageContext} from "../../components/hoc/withPageContext";
import ContainerBox from "../../components/ContainerBox";
import {Songs} from "../../components/songs/Songs";
import {useContext, useState} from "react";
import {PlaylistTitle} from "../../components/playlists/PlaylistDetail";
import {YoutubeSearch} from "../../components/songs/YoutubeSearch";

export const SongAddPage = () => {
  const context = useContext(PageContext)
  const [currentPlaylist] = useState(context.currentPlaylist)
  const [songs] = useState(context.songs)

  return (
    <ContainerBox
      left={<Songs playlist={currentPlaylist} songs={songs}/>}
      right={
        <>
          <PlaylistTitle playlist={currentPlaylist}/>
          <YoutubeSearch playlist={currentPlaylist}/>
        </>
      }
    />
  )
}

export default withPageContext()(SongAddPage)