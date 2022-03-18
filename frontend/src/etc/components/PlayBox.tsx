import React from "react";
import { PlayBoxProps } from "../shared/Props";
import { userObj } from "../test/user";
import Header from "./Header";
import ProfileMenu from "./ProfileMenu";

const PlayBox: React.FC<PlayBoxProps> = ({ children }) => {
  return (
    <div className="play-box__container">
      <Header />
      {children}
      <ProfileMenu name={userObj.nickname} />
    </div>
  );
};

export default PlayBox;
