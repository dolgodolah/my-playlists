import { Link } from "react-router-dom";
import { Icon } from "@iconify/react";
import { EditBoxProps } from "../shared/Props";

const EditBox = ({ pathname }: EditBoxProps) => {
  return (
    <div className="edit-box__container">
      <Link to="/playlist/add">
        <Icon icon="ic:baseline-playlist-add" />
      </Link>
      {/* Link, pid, sid 받아오기. */}
      {pathname === "/playlistDetail" ? (
        <>
          <Icon icon="fluent:text-bullet-list-square-edit-20-regular" />
          <Icon icon="carbon:delete" />
          <Icon icon="bi:bookmark-star-fill" />
        </>
      ) : pathname === "/songDetail" ? (
        <Icon icon="carbon:music-add" />
      ) : null}
    </div>
  );
};

export default EditBox;
