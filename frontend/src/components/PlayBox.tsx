import React from "react";
import { PlayBoxProps } from "../shared/Props";
import { user } from "../test/user";
import Header from "./Header";
import ProfileMenu from "./ProfileMenu";

const PlayBox = ({ top, title, sub, left, right }: PlayBoxProps) => {
  return (
    <div className="play-box__container">
      <Header />
      <div className="play-box__container--top">
        <span className="page__span--title">{title}</span>
        <span className="page__span--sub">{sub}</span>
        {top}
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
