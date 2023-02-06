import ContainerBox from "../../components/ContainerBox";
import React, {useState} from "react";
import withPageContext, {PageContext} from "../../components/hoc/withPageContext";
import {PlaylistAddForm} from "../../components/playlists/PlaylistAddForm";
import {MyPlaylists} from "../../components/playlists/Playlists";

export const PlaylistAddPage = () => {
  const context = { ...React.useContext(PageContext) }
  const [playlists] = useState(context.playlists)

  return (
    <ContainerBox
      left={<MyPlaylists playlists={playlists} />}
      right={<PlaylistAddForm />}
    />
  );
}

export default withPageContext()(PlaylistAddPage)