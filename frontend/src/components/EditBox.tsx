import { Link, useNavigate } from "react-router-dom";
import { Icon } from "@iconify/react";
import classNames from "classnames";
import {useEffect, useState} from "react";
import { PlaylistProps } from "../shared/Props";
import axios from "axios";
import StatusCode from "../shared/StatusCode";
import alertError from "../shared/Error";

interface EditBoxProps {
  playlist: PlaylistProps;
}

const EditBox = ({ playlist }: EditBoxProps) => {
  const navigate = useNavigate();
  const [isBookmark, setBookmark] = useState(false);
  useEffect(() => {
    axios.get(`/playlist/${playlist.playlistId}`).then((res) => {
      const response = res.data
      switch (response.statusCode) {
        case StatusCode.OK:
          setBookmark(response.isBookmark)
          break;
        default:
          alertError(response.message)
          break;
      }
    })
  }, [playlist && playlist.playlistId])

  const toggleBookmark = () => {
    axios.post(`/bookmark/${playlist.playlistId}`).then((res) => {
      const response = res.data;
      switch (response.statusCode) {
        case StatusCode.OK:
          setBookmark(!isBookmark);
          break;
        default:
          alertError(response.message);
          break;
      }
    })
  };

  const deletePlaylist = () => {
    const ok = window.confirm("플레이리스트를 삭제하시겠습니까?");
    if (ok) {
      axios.delete(`/playlist/${playlist.playlistId}`).then((res) => {
        const response = res.data;
        switch (response.statusCode) {
          case StatusCode.OK:
            navigate("/");
            break;
          default:
            alertError(response.message);
            break;
        }
      });
    }
  };
  return (
    <div className="edit-box__container">
      <Icon
        icon="bi:star-fill"
        className={classNames({
          isBookmark,
        })}
        onClick={toggleBookmark}
      />
      <Link
        to="/playlist"
        state={{
          page: "searchSong",
          playlist: playlist,
        }}
      >
        <Icon icon="carbon:music-add" />
      </Link>
      <Icon icon="carbon:delete" onClick={deletePlaylist} />
    </div>
  );
};

export default EditBox;
