import ContainerBox from "../components/ContainerBox";
import {AllPlaylists, Bookmarks, MyPlaylists, SearchPlaylists} from "../components/playlist/Playlists";
import PlaylistAddForm from "../components/playlist/PlaylistAddForm";
import {useLocation} from "react-router-dom";
import PlaylistCategory from "../components/playlist/PlaylistCategory";

interface StateProps {
  page: string;
}

export const Playlist = () => {
  const { page } = (useLocation().state as StateProps) || {
    page: "myPlaylist",
  };

  const render = () => {
    switch (page) {
      case "myPlaylist":
        return (
          <ContainerBox
            left={<MyPlaylists />}
            right={<PlaylistCategory page={page} />}
          />
        );
        break;
      case "allPlaylist":
        return (
          <ContainerBox
            left={<AllPlaylists />}
            right={<PlaylistCategory page={page} />}
          />
        );
        break;
      case "bookmarks":
        return (
          <ContainerBox
            left={<Bookmarks />}
            right={<PlaylistCategory page={page} />}
          />
        );
      default:
        return null;
    }
  }

  return render();
};

export const PlaylistAdd = () => {
  return (
    <ContainerBox
      left={<MyPlaylists />}
      right={<PlaylistAddForm />}
    />
  );
}

export const PlaylistSearch = () => {
  return (
    <ContainerBox
      left={<SearchPlaylists />}
      right={null}
    />
  );
};