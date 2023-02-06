import ContainerBox from "../components/ContainerBox";
import {MyPlaylists} from "../components/playlists/Playlists";
import React, {useState} from "react";
import withPageContext, {PageContext} from "../components/hoc/withPageContext";
import {MeForm} from "../components/MeForm";

const MePage = () => {
  const context = { ...React.useContext(PageContext) }
  const [playlists] = useState(context.playlists)

  return (
    <ContainerBox
      left={<MyPlaylists playlists={playlists}/>}
      right={<MeForm context={context}/>}
    />
  )
}

export default withPageContext()(MePage)