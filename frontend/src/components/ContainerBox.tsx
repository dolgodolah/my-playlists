import React from "react";
import { ContainerBoxProps } from "../shared/Props";
import ProfileMenu from "./ProfileMenu";

const ContainerBox = ({ left, right }: ContainerBoxProps) => {
  return (
    <div className="container-box">
      <div className="container-box--left">
        {left}
        <ProfileMenu />
      </div>
      <div className="container-box--right">
        {right}
      </div>

    </div>
  );
};

export default ContainerBox;
