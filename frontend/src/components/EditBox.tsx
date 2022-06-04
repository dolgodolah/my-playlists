import { Link, useNavigate } from "react-router-dom";
import { Icon } from "@iconify/react";
import classNames from "classnames";
import { useState } from "react";
import {PlaylistProps} from "../shared/Props";

interface EditBoxProps {
  playlist: PlaylistProps;
}

const EditBox = ({ playlist }: EditBoxProps) => {
  const navigate = useNavigate();
  const [isBookmarked, setIsBookmarked] = useState(false);
  const setBookmark = () => {
    // TODO: 북마크 설정 로직 추가
    setIsBookmarked(!isBookmarked);
  };
  const deletePlaylist = () => {
    const ok = window.confirm("플레이리스트를 삭제하시겠습니까?");
    if (ok) {
      // TODO: 플리 삭제 로직 추가
      navigate("/");
    }
  };
  return (
    <div className="edit-box__container">
      {" "}
      <Icon
        icon="bi:star-fill"
        className={classNames({
          isBookmarked,
        })}
        onClick={setBookmark}
      />
      <Link
        to="/playlist"
        state={{
          page: "searchSong",
          playlist: playlist
        }}
      >
        <Icon icon="carbon:music-add" />
      </Link>
      <Icon icon="carbon:delete" onClick={deletePlaylist} />
    </div>
  );
};

export default EditBox;
