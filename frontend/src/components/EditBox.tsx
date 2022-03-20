import { Link } from "react-router-dom";
import { Icon } from "@iconify/react";
import { EditBoxProps } from "../shared/Props";

const EditBox = ({ page }: EditBoxProps) => {
  return (
    <div className="edit-box__container">
      {/* Link, pid, sid 받아오기. */}
      {page === "/playlistDetail" ? (
        <>
          <Icon icon="fluent:text-bullet-list-square-edit-20-regular" />
          <Icon icon="carbon:delete" />
          <Icon icon="bi:bookmark-star-fill" />
        </>
      ) : page === "/songDetail" ? (
        <Icon icon="carbon:music-add" />
      ) : null}
    </div>
  );
};

export default EditBox;
