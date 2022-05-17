import React from "react";
import { PlayBoxProps } from "../shared/Props";
import Header from "./Header";
import ProfileMenu from "./ProfileMenu";

const PlayBox = ({ top, left, right }: PlayBoxProps) => {
  return (
    <div className="play-box__container">
      <Header />
      <div className="play-box__container--top">{top}</div>
      <div className="play-box__container--left">
        <div className="lists__container">{left}</div>
      </div>
      <div className="play-box__container--right">{right}</div>
      <ProfileMenu />
    </div>
  );
};

export default PlayBox;
