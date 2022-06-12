import { Icon } from "@iconify/react";
import {useCallback, useEffect, useState} from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";
import {PlaylistProps, SongProps} from "../shared/Props";

interface SongsProps {
  playlist: PlaylistProps;
}

const Songs = ({ playlist }: SongsProps) => {
  const [songs, setSongs] = useState([]);

  useEffect(() => {
    axios.get("/songs", { params: { playlistId: playlist.playlistId } }).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          setSongs(response.songs);
          break;
        default:
          alertError(response.message)
          break;
      }
    })
  }, []);

  return (
    <>
      <div className="lists__container">
        {songs?.map((song: SongProps) => (
          <Link
            key={song.songId}
            to="/playlist"
            state={{
              page: "playSong",
              playlist: playlist,
              playedSong: song,
            }}
          >
            <div className="song__container">
              <span className="song__span--title">{song.title}</span>
              <div>
                <span className="song__span--createdDate">
                  {song.createdDate}
                </span>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </>
  );
};

export default Songs;
