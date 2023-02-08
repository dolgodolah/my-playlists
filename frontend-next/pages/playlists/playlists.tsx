import withPageContext, {PageContext} from "../../components/hoc/withPageContext"
import React, {useState} from "react";
import ContainerBox from "../../components/ContainerBox";
import {AllPlaylists, MyPlaylists} from "../../components/playlists/Playlists";
import Category, {CategoryType} from "../../components/playlists/Category";
import StatusCode from "../../shared/StatusCode";
import useClient from "../../components/hooks/useClient";
import alertError from "../../shared/Error";

const PlaylistPage = () => {
  const context = { ...React.useContext(PageContext) }
  const [category, setCategory] = useState(context.category)
  const [playlists, setPlaylists] = useState(context.playlists)
  const client = useClient()

  const getPlaylists = async (category: CategoryType) => {
    const res = await client.get(`/${category}`)
    switch (res.statusCode) {
      case StatusCode.OK:
        setPlaylists(res.playlists)
        setCategory(category)
        break
      default:
        alertError(res)
        break
    }
  }

  const renderPlaylists = () => {
    switch (category) {
      case "my-playlists":
        return <MyPlaylists playlists={playlists} />
      case "all-playlists":
        return <AllPlaylists playlists={playlists} />
      case "bookmarks":
        return <></>
    }
  }

  return (
    <ContainerBox
      left={renderPlaylists()}
      right={<Category category={category} getPlaylists={(category) => getPlaylists(category)} />}
    />
  )
}

export default withPageContext()(PlaylistPage)