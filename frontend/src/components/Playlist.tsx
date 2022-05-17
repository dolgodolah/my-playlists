import { Icon } from "@iconify/react";
import { Link } from "react-router-dom";
import { PlaylistProps } from "../shared/Props";
import {useEffect, useState} from "react";
import axios from "axios";

interface PageProps {
  page: string;
}

const Playlist = ({ page }: PageProps) => {
  const [playlists, setPlaylists] = useState([]) as Array<any>;
  useEffect(() => {
    let api = "";
    switch (page) {
      case "myPlaylist":
        api = "/my_playlists";
        break;
      case "allPlaylist":
        api = "/all_playlists";
        break;
      case "bookmarks":
        api = "/bookmarks"
        break;
    }

    axios
      .get(api)
      .then(function (response) {
        const playlists: Array<any> = response.data.playlists
        setPlaylists(playlists)
      })
      .catch(function (error) {
        console.log(error);
      });
  }, [])

  return (
    playlists.map((playlist : PlaylistProps) => (
      <Link key={playlist.playlistId}
        to="/playlist"
        state={{
          page: "showSongs",
        }}
        className="playlist__link"
      >
        <div className="playlist__container">
          <div className="playlist__container--left">
            <span className="playlist__span--title">{playlist.title}</span>
            <div>
            <span className="playlist__span--updatedDate">
              {playlist.updatedDate}
            </span>
              <span className="playlist__span--author">{playlist.author}</span>
            </div>
          </div>
          <div className="playlist__container--right">
            <span className="playlist__span--amount">{playlist.songCount}</span>
            <div className="playlist-icon__container">
              <Icon icon="bxs:playlist" />
            </div>
          </div>
          <div className="hidden-background__container">
          <span className="hidden-background__containe__span">
            <Icon icon="codicon:debug-start" />
          </span>
          </div>
        </div>
      </Link>
    ))

  );
};

export default Playlist;
