import React from "react";
import { PlayBoxProps } from "../shared/Props";
import { songs, user } from "../test/user";
import Header from "./Header";
import ProfileMenu from "./ProfileMenu";

const PlayBox = ({ page, top, left, right }: PlayBoxProps) => {
  return (
    <div className="play-box__container">
      <Header />
      <div className="play-box__container--top">
        <span className="page-title__span">{top}</span>
      </div>
      <div className="play-box__container--left">
        <div className="lists__container">{left}</div>
      </div>
      <div className="play-box__container--right">{right}</div>
      <ProfileMenu name={user.nickname} />
    </div>
  );
};

export default PlayBox;
