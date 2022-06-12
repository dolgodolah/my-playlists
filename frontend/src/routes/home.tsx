import {useLocation} from "react-router-dom";
import PlayBox from "../components/PlayBox";
import Playlists from "../components/Playlists";
import PlaylistCategory from "../components/PlaylistCategory";
import Header from "../components/Header";
import React from "react";
import SearchType from "../shared/SearchType";

interface StateProps {
  page: string;
}

const Home = () => {
  const { page } = (useLocation().state as StateProps) || {
    page: "myPlaylist",
  };
  return (
    <PlayBox
      left={
        <>
          <Header type={SearchType.PLAYLIST} />
          <Playlists page={page} />
        </>
      }
      right={<PlaylistCategory page={page} />}
    />
  );
};

export default Home;
