import React from "react";
import { PlayBoxProps } from "../shared/Props";
import ProfileMenu from "./ProfileMenu";

const PlayBox = ({ left, right }: PlayBoxProps) => {
  return (
    <div className="play-box__container">
      <div className="play-box__container--left">
        {left}
        <ProfileMenu />
      </div>
      <div className="play-box__container--right">{right}</div>

    </div>
  );
};

export default PlayBox;
