import withPageContext, {PageContext} from "../../components/hoc/withPageContext"
import React, {useState} from "react";
import ContainerBox from "../../components/ContainerBox";
import {Playlists} from "../../components/playlists/Playlists";
import Category, {CategoryType} from "../../components/playlists/Category";
import StatusCode from "../../shared/StatusCode";
import useClient from "../../components/hooks/useClient";
import alertError from "../../shared/Error";

const PlaylistPage = () => {
  const context = { ...React.useContext(PageContext) }
  const [page, setPage] = useState(context.page)
  const [playlists, setPlaylists] = useState(context.playlists)
  const client = useClient()

  const getPlaylists = async (category: CategoryType) => {
    const res = await client.get(`/${category}`)
    switch (res.statusCode) {
      case StatusCode.OK:
        setPlaylists(res.playlists)
        setPage(category)
        break
      default:
        alertError(res)
        break
    }
  }

  return (
    <ContainerBox
      left={<Playlists playlists={playlists}/>}
      right={<Category page={page} getPlaylists={(pageType) => getPlaylists(pageType)} />}
    />
  )
}

export default withPageContext()(PlaylistPage)