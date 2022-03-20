import React from "react";
import { PlayBoxProps } from "../shared/Props";
import { songs, user } from "../test/user";
import Header from "./Header";
import ProfileMenu from "./ProfileMenu";
import EditBox from "./EditBox";

const PlayBox = ({ page, top, left, right }: PlayBoxProps) => {
  return (
    <div className="play-box__container">
      <Header />
      <div className="play-box__container--top">
        {top}
        <EditBox page={page} />
      </div>
      <div className="play-box__container--left">{left}</div>
      <div className="play-box__container--right">{right}</div>
      <ProfileMenu name={user.nickname} />
    </div>
  );
};

export default PlayBox;
