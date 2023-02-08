import withPageContext, {PageContext} from "../../components/hoc/withPageContext"
import ContainerBox from "../../components/ContainerBox";
import {Songs} from "../../components/songs/Songs";
import {PlaylistDescription, PlaylistTitle} from "../../components/playlists/PlaylistDetail";
import {useContext, useState} from "react";

const SongsPage = () => {
  const context = useContext(PageContext)
  const [currentPlaylist] = useState(context.currentPlaylist)
  const [songs] = useState(context.songs)
  return (
    <ContainerBox
      left={<Songs songs={songs} playlist={currentPlaylist}/>}
      right={
        <>
          <PlaylistTitle playlist={currentPlaylist}/>
          <PlaylistDescription playlist={currentPlaylist}/>
        </>
      }
    />
  )
}

export default withPageContext()(SongsPage)